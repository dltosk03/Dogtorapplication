package com.example.dogtorapplication

import TodoAdapter
import Todoupdate
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.dogtorapplication.CalendarUtil.selectedDate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_myinformation.*
import kotlinx.android.synthetic.main.fragment_community.view.*
import kotlinx.android.synthetic.main.item_calendar_body.*
import kotlinx.android.synthetic.main.layout_todo.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.todo_add.*
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates

var gotomain : Boolean = false
val java = yearmothday()
var input : String? = ""
var dbHelper : MyDBHelper ?=null
var dotHelper : DotDBHelper ?=null
var listsize : Int? =0
var view2 : View? = null

class CareFragment : DialogFragment(), OnItemListener {
    var aa : Boolean = false
    val java1 = yearmothday()
    val fragment : Fragment = this
    val fragmentM : FragmentManager? = getFragmentManager()
    var recyclerView: RecyclerView? = null
    var red : ImageButton? =null
    var orange : ImageButton? =null
    var yello : ImageButton? =null
    var green : ImageButton? =null
    var blue : ImageButton? =null
    var bluepurple : ImageButton? =null
    var purple : ImageButton? =null
    var pink : ImageButton? =null

    var recyclerView2: RecyclerView? = null
    var recyclerView3: RecyclerView?=null
    var monthYearText: TextView? = null
    var nolist : TextView? =null

    //var selectedDate : LocalDate? =null
    //val mActivity = activity as MainActivity
    var ct: Context? = null
    var updateBtn : FloatingActionButton? = null

    //db
    //lateinit var dbHelper: MyDBHelper
    lateinit var database: SQLiteDatabase
    //private lateinit var binding: CareFragment
    var checkInt : Int? =0
    //db??? ?????????????????? ??????
    lateinit var todoList : TextView
    lateinit var rectangle : ImageView

    //db??? ???????????? ?????? ??????
    class Main() {
        var input2: String? = input
        var size : Int? = listsize
    }
    //db
/*    lateinit var adapter: TodoAdapter
    lateinit var myHelper : MyDBHelper*/
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ct = container?.getContext();
        val v1: View = inflater.inflate(R.layout.todo_add, container, false)
        val button = v1.findViewById<ImageButton>(R.id.todo_out_btn) as ImageButton
        val v2 = inflater.inflate(R.layout.item_calendar_body, container, false)
        //v2!!.findViewById<FloatingActionButton>(R.id.update_btn).setVisibility(View.VISIBLE)
        val item_calendar_body = inflater.inflate(R.layout.item_calendar_body, container, false)
        val layout_todo = inflater.inflate(R.layout.layout_todo, container, false)
        val v3 : View = inflater.inflate(R.layout.calendar_cell,container,false)
        //val aaa = inflater.inflate(R.layout.test,container,false)
        monthYearText = v2?.findViewById<TextView>(R.id.monthYearText) as TextView
        recyclerView = v2?.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        recyclerView3 = v3.findViewById(R.id.recyclerView3) as RecyclerView
        //recyclerView2 = v2.findViewById<RecyclerView>(R.id.recycleView2)as RecyclerView
        //db ?????? ????????? ?????????
        todoList = layout_todo.findViewById(R.id.todoList)
        rectangle = layout_todo.findViewById(R.id.rectangle)
        red = layout_todo.findViewById(R.id.color_red)
        orange = layout_todo.findViewById(R.id.color_red)
        yello = layout_todo.findViewById(R.id.color_red)
        green = layout_todo.findViewById(R.id.color_red)
        blue = layout_todo.findViewById(R.id.color_red)
        bluepurple = layout_todo.findViewById(R.id.color_red)
        purple = layout_todo.findViewById(R.id.color_red)
        pink = layout_todo.findViewById(R.id.color_red)


/*
        myHelper = MyDBHelper(ct!!,"groupDo",1)
        adapter = TodoAdapter()

        val recycleView2 : RecyclerView = v2.findViewById<RecyclerView>(R.id.recycleView2)as RecyclerView
        val dbHelper = MyDBHelper(ct, "mydb.db", 1)
        val adapter: TodoAdapter = TodoAdapter()

        val memos = dbHelper.selectMemo()
        adapter.lisData.addAll(memos)
        recycleView2.adapter = adapter
        recycleView2.layoutManager = LinearLayoutManager(this)

        val buttonSave : Button = test.findViewById(R.id.buttonSave)
        val editMemo : EditText = findViewById(R.id.editMemo)
        buttonSave.setOnClickListener{
            val content = editMemo.text.toString()
            Log.d("??????","content = $content")
            if(content.isNotEmpty()){
                val memo = Memo(null,1,content,System.currentTimeMillis())
                dbHelper.insertMemo(memo)
                Log.d("??????","content is not empty")
            }
        }*/
        return inflater.inflate(R.layout.item_calendar_body, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        nolist = view.findViewById<TextView>(R.id.nolist)
        monthYearText = view.findViewById(R.id.monthYearText)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView2 = view.findViewById<RecyclerView>(R.id.recycleView2)
        //recyclerView3 = view.findViewById(R.id.recyclerView3)
        val button = view.findViewById<ImageButton>(R.id.todo_out_btn)
        var listscrollBtn = view.findViewById<ImageButton>(R.id.listscroll)
        var plusBtn = view.findViewById<ImageButton>(R.id.plus_btn)

        updateBtn = view.findViewById<FloatingActionButton>(R.id.update_btn)
        selectedDate = LocalDate.now()
        // ????????? ??? ???????????? ??????
        setMonthView()
        Log.d("??????", "set here7")

        listscrollBtn.setOnClickListener {
            dbHelper = MyDBHelper(ct, Main().input2)
            setTodoView()
        }

        plusBtn.setOnClickListener {
            val dialog = CustomDialog(ct!!)
            // Custom Dialog ??????
            dialog.showDialog(false,0,0)
        }
        /*updateBtn?.setOnClickListener {
            val dialog = CustomDialog(ct!!)
            // Custom Dialog ??????
            dialog.showDialog(true, Todoupdate().num!!+1)
        }

        //???????????? onclick ????????? ????????? ?????? dialog ?????? ?????? ????????? ????????? ...
        Todoupdate().buf_view4?.findViewById<ImageButton>(R.id.updatebtn)?.setOnClickListener {
            Log.d("aa","click???????????????????????????")
            val dialog = CustomDialog(ct!!)
            // Custom Dialog ??????
            dialog.showDialog(false,0)
        }*/


    }
    fun visi(){
        updateBtn?.setVisibility(View.VISIBLE)
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MM??? yyyy")
        return date!!.format(formatter)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val dayList = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val lastDay = yearMonth.lengthOfMonth()
        val firstDay = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstDay.dayOfWeek.value
        if ((7 - dayOfWeek) == 0) {
            for (i in 1..42) {
                if (i <= dayOfWeek || i > lastDay + dayOfWeek && (i - dayOfWeek) <= 0) {
                    continue
                } else if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                    dayList.add("")
                } else {
                    dayList.add((i - dayOfWeek).toString())
                }
            }
        } else {
            for (i in 1..42) {
                if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                    dayList.add("")
                } else {
                    dayList.add((i - dayOfWeek).toString())
                }
            }
        }
        return dayList
    }

    //getActivity()?.getApplicationContext()
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun setMonthView() {
        monthYearText!!.text = monthYearFromDate(selectedDate)
        val dayList = daysInMonthArray(selectedDate)
        val adapter = CalendarAdapter(dayList, this)
        val manager: RecyclerView.LayoutManager =
            GridLayoutManager(ct, 7)//GridLayoutManager(applicationContext, 7)
        recyclerView!!.layoutManager = manager
        recyclerView!!.adapter = adapter
        dotHelper = DotDBHelper(ct, "2023??? 01???2???DOT")
        setDotView()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setid(id : Int){
        if(id==1) {
            val dialog = CustomDialog(ct!!)
            // Custom Dialog ??????
            dialog.showDialog(true, Todoupdate().num!! , 1)
        } else if(id==2){
            val dialog = CustomDialog(ct!!)
            dbHelper = MyDBHelper(ct, Main().input2)
            Log.d("num", "${Todoupdate().num!! }")
            dbHelper!!.deleteMemo(Todoupdate().num!! )
            Log.d("num2", "${Todoupdate().num!! }")
            var adapter = TodoAdapter(this@CareFragment)
            var memos = dbHelper!!.selectMemo()
            Log.d("??????", "set here")
            adapter.listDate.addAll(memos)
            Log.d("??????", "set here2")
            recycleView2.adapter = adapter
            Log.d("??????", "set here3")
            recycleView2.layoutManager = GridLayoutManager(ct,1)
            //Log.d("??????", "content here4")
            adapter.listDate.clear()
            adapter.listDate.addAll(dbHelper!!.selectMemo())
            adapter.notifyDataSetChanged()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDotView(){
        val dayList = daysInMonthArray(selectedDate)
        val adapter = DotAdapter(dayList)
        var memos = dotHelper!!.selectMemo()
        Log.d("??????", "set here")
        adapter.listdot.addAll(memos)
        Log.d("??????", "set here2")
        listsize= adapter.listdot.size
        Log.d("??????", "set here3")
        val manager: RecyclerView.LayoutManager =
            GridLayoutManager(ct, 3)
        Log.d("??????", "set here4")
        recyclerView3!!.layoutManager = manager
        Log.d("??????", "set here5")
        recyclerView3!!.adapter = adapter
        Log.d("??????", "set here6")
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun setTodoView() {
        var adapter = TodoAdapter(this)
        var memos = dbHelper!!.selectMemo()
        Log.d("??????", "set here")
        adapter.listDate.addAll(memos)
        listsize= adapter.listDate.size
        recycleView2.adapter = adapter
        Log.d("??????", "set here3")
        recycleView2.layoutManager = GridLayoutManager(ct,1)
        if(listsize==0){
            nolist!!.setVisibility(View.VISIBLE)
        }
        else{
            nolist!!.setVisibility(View.GONE)

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun yearMonthFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy??? MM???")
        return date!!.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(dayText: String) {
        var yearMonday = yearMonthFromDate(selectedDate) + "" + dayText + "???"

        //?????? ??????
        input = yearMonday
        //?????????
        if(Main().size!=0){
        Toast.makeText(ct, "Todo list ????????? ?????? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(ct, "Todo list ????????? ?????? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
        }
    }

    inner class CustomDialog(context: Context) {
        private lateinit var onItemListener: OnItemListener
        private lateinit var list_onclick_interface : list_onclick_interface
        private lateinit var binding: CustomDialog
        private val dialog = Dialog(context)
        private lateinit var onClickListener: OnDialogClickListener


        /*fun onCreate() {
            setContentView(R.layout.item_calendar_body)
            val todo_body_layout = findViewById<LinearLayout>(R.id.todo_body_layout)
            todo_body_layout.addView(createimage())
        }*/

        fun setOnClickListener(listener: OnDialogClickListener) {
            onClickListener = listener
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun showDialog(run : Boolean, num : Int, num2 : Int) {
            //color ??????
            var color: Int = 1
            //????????? ????????? ??????
            var text = CareFragment.Main().input2
            if (text != null) {
                java.setyearmonthday(text)
                java.setYear(text.substring(0 until 4))
                java.setMonth(text.substring(6 until 8))
                java.setDay(text.substring(9 until 11))
            }
            if (text != null) {
                java.setMonth(text.substring(6 until 8))
            }
            if(num2==0){
                dialog.setContentView(R.layout.todo_add)
            }
            if(num2==1){
                dialog.setContentView(R.layout.todo_fix)
                if(run){
                    var a : String= dbHelper!!.selectText(num)
                    var b : String= dbHelper!!.selectText2(num)
                    var c : Int = dbHelper!!.selectColor(num)
                    colorfixdialogret(c)
                    Log.d("text","$a")
                    dialog.editMemo.setText(a)
                    dialog.editMemo2.setText(b)
                }
            }
            if(num2==2){
                dbHelper!!.deleteMemo(num)
            }

            dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.setCanceledOnTouchOutside(true)
            dialog.setCancelable(true)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            // Custom Dialog ?????? ??????
            dialog.window?.setGravity(Gravity.BOTTOM)
            // Custom Dialog ?????? ?????? (????????? ?????? ???????????? ?????? ?????? ?????? ?????????)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            text = java.getYear() + "." + java.getMonth() + "." + java.getDay()
            //?????? ????????? ????????? ??????
            dialog.click_day.text = java.getYear() + "." + java.getMonth() + "." + java.getDay()
            /*if(run){
                var a : String= dbHelper!!.selectText(num)
                Log.d("text","$a")
                dialog.editMemo.setText(a)
            }*/
            dialog.show()

            dialog.todo_out_btn.setOnClickListener {
                //??? ??????
                dialog.dismiss()
                //onCreate()
            }

            dialog.todo_add_btn.setOnClickListener {
                var dottable = Main().input2+"DOT"
                dotHelper = DotDBHelper(ct, dottable)
                val memo = CheckMemo(null, color)
                dotHelper!!.insertMemo(memo)
                val dayList = daysInMonthArray(selectedDate)
                val dotadpater = DotAdapter(dayList)
                var dotmemos = dotHelper!!.selectMemo()
                dotadpater.listdot.addAll(dotmemos)
                recyclerView3?.adapter = dotadpater
                recyclerView3?.layoutManager = GridLayoutManager(ct,3)
                //Calendar().buf_view2?.findViewById<RecyclerView>(R.id.recyclerView3).

                Log.d("DBTABLE", "${Main().input2}")
                dbHelper = MyDBHelper(ct, Main().input2)
                val content = dialog.editMemo.text.toString()
                val content2 = dialog.editMemo2.text.toString()
                val check = 1
                Log.d("??????", "content!!!!!!! = $content")
                if (content.isNotEmpty()) {
                    val memo = Memo(null, color, content,content2)
                    Log.d("??????", "memo is inserted")
                    Log.d("??????", "helper is not empty, content!!!!!!! = $dbHelper")
                    if(run){
                        dbHelper!!.updateMemo(memo,num)
                    } else {
                        dbHelper!!.insertMemo(memo)
                    }
                    Log.d("??????", "content is not empty")
                }
                var adapter = TodoAdapter(this@CareFragment)
                var memos = dbHelper!!.selectMemo()
                Log.d("??????", "set here")
                adapter.listDate.addAll(memos)
                Log.d("??????", "set here2")
                recycleView2.adapter = adapter
                Log.d("??????", "set here3")
                recycleView2.layoutManager = GridLayoutManager(ct,1)
                //Log.d("??????", "content here4")
                adapter.listDate.clear()
                adapter.listDate.addAll(dbHelper!!.selectMemo())
                adapter.notifyDataSetChanged()
                nolist!!.setVisibility(View.GONE)

                dialog.dismiss()
            }
            dialog.color_red.setOnClickListener {
                dialogret()
                color = 1
                /*Calendar().buf_view2?.findViewById<ImageView>(R.id.todo1)
                    ?.setBackgroundResource(R.drawable.check_round)*/
                dialog.color_red.setBackgroundResource(R.drawable.group_57)
            }
            dialog.color_orange.setOnClickListener {
                dialogret()
                color = 2
                /*Calendar().buf_view2?.findViewById<ImageView>(R.id.todo1)
                    ?.setBackgroundResource(R.drawable.check_round_orange)*/
                dialog.color_orange.setBackgroundResource(R.drawable.group_58)
            }
            dialog.color_blue.setOnClickListener {
                dialogret()
                color = 5
                /*Calendar().buf_view2?.findViewById<ImageView>(R.id.todo1)
                    ?.setBackgroundResource(R.drawable.check_round_blue)*/
                dialog.color_blue.setBackgroundResource(R.drawable.group_61)
            }
            dialog.color_blue_purple.setOnClickListener {
                dialogret()
                color = 6
                /*Calendar().buf_view2?.findViewById<ImageView>(R.id.todo1)
                    ?.setBackgroundResource(R.drawable.check_round_bluepurple)*/
                dialog.color_blue_purple.setBackgroundResource(R.drawable.group_62)
            }
            dialog.color_green.setOnClickListener {
                dialogret()
                color = 4
                /*Calendar().buf_view2?.findViewById<ImageView>(R.id.todo1)
                    ?.setBackgroundResource(R.drawable.check_round_green)*/
                dialog.color_green.setBackgroundResource(R.drawable.group_60)
            }
            dialog.color_pink.setOnClickListener {
                dialogret()
                color = 8
                /*Calendar().buf_view2?.findViewById<ImageView>(R.id.todo1)
                    ?.setBackgroundResource(R.drawable.check_round_pink)*/
                dialog.color_pink.setBackgroundResource(R.drawable.group_56)
            }
            dialog.color_purple.setOnClickListener {
                dialogret()
                color = 7
                /*Calendar().buf_view2?.findViewById<ImageView>(R.id.todo1)
                    ?.setBackgroundResource(R.drawable.check_round_purple)*/
                dialog.color_purple.setBackgroundResource(R.drawable.group_63)
            }
            dialog.color_yello.setOnClickListener {
                dialogret()
                color = 3
                /*Calendar().buf_view2?.findViewById<ImageView>(R.id.todo1)
                    ?.setBackgroundResource(R.drawable.check_round_yello)*/
                dialog.color_yello.setBackgroundResource(R.drawable.group_59)
            }
            dialog.tag_no.setOnClickListener{
                tagdialogret()
                dialog.tag_no.setBackgroundResource(R.drawable.rounded_corner)
                dialog.tag_no.setTextColor(Color.parseColor("#FFFFFF"))
            }
            dialog.tag_medicine.setOnClickListener{
                tagdialogret()
                dialog.tag_medicine.setBackgroundResource(R.drawable.rounded_corner)
                dialog.tag_medicine.setTextColor(Color.parseColor("#FFFFFF"))
            }
            dialog.tag_cut.setOnClickListener {
                tagdialogret()
                dialog.tag_cut.setBackgroundResource(R.drawable.rounded_corner)
                dialog.tag_cut.setTextColor(Color.parseColor("#FFFFFF"))
            }
            dialog.tag_walk.setOnClickListener{
                tagdialogret()
                dialog.tag_walk.setBackgroundResource(R.drawable.rounded_corner)
                dialog.tag_walk.setTextColor(Color.parseColor("#FFFFFF"))
            }

        }
        fun dialogret(){
            dialog.color_red.setBackgroundResource(R.drawable.red_circle)
            dialog.color_orange.setBackgroundResource(R.drawable.orange)
            dialog.color_yello.setBackgroundResource(R.drawable.yello)
            dialog.color_green.setBackgroundResource(R.drawable.green)
            dialog.color_blue.setBackgroundResource(R.drawable.blue)
            dialog.color_blue_purple.setBackgroundResource(R.drawable.bluepurple)
            dialog.color_purple.setBackgroundResource(R.drawable.purple)
            dialog.color_pink.setBackgroundResource(R.drawable.pink)
        }
        fun tagdialogret(){
            dialog.tag_no.setBackgroundResource(R.drawable.rounded_corner_white)
            dialog.tag_no.setTextColor(Color.parseColor("#5FA8D3"))
            dialog.tag_medicine.setBackgroundResource(R.drawable.rounded_corner_white)
            dialog.tag_medicine.setTextColor(Color.parseColor("#5FA8D3"))
            dialog.tag_cut.setBackgroundResource(R.drawable.rounded_corner_white)
            dialog.tag_cut.setTextColor(Color.parseColor("#5FA8D3"))
            dialog.tag_walk.setBackgroundResource(R.drawable.rounded_corner_white)
            dialog.tag_walk.setTextColor(Color.parseColor("#5FA8D3"))
        }
        fun tagfixdialogret(num : Int){
            if(num==1){
                dialog.tag_no.setBackgroundResource(R.drawable.rounded_corner)
                dialog.tag_no.setTextColor(Color.parseColor("#FFFFFF"))
            } else if(num==2){
                dialog.tag_medicine.setBackgroundResource(R.drawable.rounded_corner)
                dialog.tag_medicine.setTextColor(Color.parseColor("#FFFFFF"))
            }else if(num==3){
                dialog.tag_cut.setBackgroundResource(R.drawable.rounded_corner)
                dialog.tag_cut.setTextColor(Color.parseColor("#FFFFFF"))
            }else if(num==4){
                dialog.tag_walk.setBackgroundResource(R.drawable.rounded_corner)
                dialog.tag_walk.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
        fun colorfixdialogret(num : Int){
            if(num==1){
                dialog.color_red.setBackgroundResource(R.drawable.group_57)
            } else if(num==3){
                dialog.color_yello.setBackgroundResource(R.drawable.group_59)
            }else if(num==2){
                dialog.color_orange.setBackgroundResource(R.drawable.group_58)
            }else if(num==4){
                dialog.color_green.setBackgroundResource(R.drawable.group_60)
            }else if(num==5){
                dialog.color_blue.setBackgroundResource(R.drawable.group_61)
            }else if(num==6){
                dialog.color_blue_purple.setBackgroundResource(R.drawable.group_62)
            }else if(num==7){
                dialog.color_purple.setBackgroundResource(R.drawable.group_63)
            }else if(num==8){
                dialog.color_pink.setBackgroundResource(R.drawable.group_56)
            }
        }
    }


}
