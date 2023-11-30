package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        intent.extras?.getInt(TASK_ID)?.let { taskId ->
            detailTaskViewModel.setTaskId(taskId)
            detailTaskViewModel.task.observe(this) { task ->
                task?.let {
                    populateTaskDetails(task)
                    setupDeleteButton()
                }
            }
        }
    }

    private fun populateTaskDetails(task: Task) {
        val edtTitle = findViewById<TextInputEditText>(R.id.detail_ed_title)
        val edtDescription = findViewById<TextInputEditText>(R.id.detail_ed_description)
        val edtDueDate = findViewById<TextInputEditText>(R.id.detail_ed_due_date)

        edtTitle.setText(task.title)
        edtDescription.setText(task.description)
        edtDueDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))
    }

    private fun setupDeleteButton() {
        findViewById<Button>(R.id.btn_delete_task).setOnClickListener {
            detailTaskViewModel.deleteTask()
            finish()
        }
    }
}