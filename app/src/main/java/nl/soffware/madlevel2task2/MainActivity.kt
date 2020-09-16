package nl.soffware.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import nl.soffware.madlevel2task2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        addData()
    }

    private fun addData() {
        Question.QUESTIONS.indices.mapTo(questions){
            Question(
                Question.QUESTIONS[it],
                Question.answers[it],
                Question.explanations[it]
            )
        }
        questionAdapter.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.rvStatements.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvStatements.adapter = questionAdapter
        binding.rvStatements.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        createLeftItemTouchHelper().attachToRecyclerView(rvStatements)
        createRightItemTouchHelper().attachToRecyclerView(rvStatements)
    }

    private fun createLeftItemTouchHelper(): ItemTouchHelper {
        return createItemTouchHelper(ItemTouchHelper.LEFT, false)
    }

    private fun createRightItemTouchHelper(): ItemTouchHelper {
        return createItemTouchHelper(ItemTouchHelper.RIGHT, true)
    }

    private fun createItemTouchHelper(direction: Int, answer:Boolean): ItemTouchHelper {
        val callback = object: ItemTouchHelper.SimpleCallback(0, direction) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val question = questions[position]

                if(answerCorrect(answer, question)) {
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                } else {
                    Snackbar.make(rvStatements, question.explanation, Snackbar.LENGTH_SHORT).show()
                    questionAdapter.notifyDataSetChanged()
                }

                if(questions.size <= 0){
                    addData()
                    Snackbar.make(rvStatements, getText(R.string.winner), Snackbar.LENGTH_LONG).show()
                }
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun answerCorrect(answer: Boolean, question: Question): Boolean {
        return answer == question.answer
    }
}