package com.example.dogtorapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_myinformation.*
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.fragment_my.view.*

class MyFragment : Fragment() {


    // 파이어 베이스 인증 및 데이터 사용 위한 객체
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail= auth?.currentUser?.email.toString() // 로그인한 이메일을 불러오기

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_my,container,false)

        val myInf = view.myInformation
        val tv = view.tv_userEmail  // email 입력할 텍스트 뷰 지정
        val logOut = view.logout // 로그아웃을 위한 변수 선언

        tv.text = userEmail // 받아온 이메일 정보로 text 설정

        myInf.setOnClickListener {// 내정보 버튼 클릭 시 이동
            val intent = Intent(activity, com.example.dogtorapplication.myinformation::class.java)
            startActivity(intent)
        }

        view.changpassword.setOnClickListener {
            findPassword()
        }

        logOut.setOnClickListener {// 로그아웃 버튼 클릭 시 로그아웃 및 로그인 화면으로 전환
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    // 비밀번호 찾기
    // sendPasswordResetEmail()로 이메일 전송
    fun findPassword(){
        FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail).addOnCompleteListener { task ->
            if(task.isSuccessful){ // 성공 시
                Toast.makeText(requireActivity(), "비밀번호 변경 메일을 전송했습니다", Toast.LENGTH_LONG).show()
            }else{ // 실패 시
                Toast.makeText(requireActivity(), task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}