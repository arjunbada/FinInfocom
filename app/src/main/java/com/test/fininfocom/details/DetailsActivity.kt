package com.test.fininfocom.details

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.test.fininfocom.R
import com.test.fininfocom.database.repository.PersonTRepository
import com.test.fininfocom.databinding.ActivityDetailsBinding
import com.test.fininfocom.details.adapter.ViewDetailsAdapter
import com.test.fininfocom.details.model.Person
import com.test.fininfocom.login.LoginActivity
import com.test.fininfocom.utils.ShowAlert


class DetailsActivity : AppCompatActivity(), ViewDetailsAdapter.OnItemClickListener {
    lateinit var binding: ActivityDetailsBinding
    lateinit var showAlert: ShowAlert
    lateinit var viewDetailsAdapter: ViewDetailsAdapter

    private var personTList: MutableList<Person> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInitialization()
    }

    private fun setupInitialization() {
        title = getString(R.string.details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)

        showAlert = ShowAlert(this@DetailsActivity)


        //set adapter
        viewDetailsAdapter =
            ViewDetailsAdapter(
                personTList,
                this@DetailsActivity
            )
        binding.recyclerViewUserDetails.adapter = viewDetailsAdapter
        binding.recyclerViewUserDetails.layoutManager =
            LinearLayoutManager(
                this@DetailsActivity,
                LinearLayoutManager.VERTICAL,
                false
            )

        dataBaseOperation()
    }

    private fun dataBaseOperation() {
        try {
            val personTRepository = PersonTRepository(this@DetailsActivity)

            //storing data to the table
            val personsList = mutableListOf(
                Person(1, "Mallikarjuna Nayak", 25, "Bidar Gaht", "Engineer"),
                Person(2, "Abhishek verma", 30, "Kundapura", "Doctor"),
                Person(3, "Nayana Preetham", 35, "Banglore", "Teacher"),
                Person(4, "Venice Rocky", 28, "San Francisco", "Software Developer"),
                Person(5, "Michael Raj", 40, "Mysore", "Lawyer"),
                Person(6, "Sophia Samantha", 22, "Miami", "Student"),
                Person(7, "Muhhamed Afnan", 33, "Jammu Kashmir", "Architect"),
                Person(8, "Emma watson", 29, "Seattle", "Graphic Designer"),
                Person(9, "Radhika Merchent", 38, "Mumbai", "Accountant"),
                Person(10, "Olivia perry", 27, "Austin", "Writer")
            )
            personTRepository.deleteRecords()
            personTRepository.insert(personsList)

            //fetching the data and displaying in recyclerview
            personTRepository.personsAll()?.let {
                personTList.addAll(it)
                viewDetailsAdapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCardDetailsClick(position: Int) {
        showAlert.toastMessage(getString(R.string.clicked_on_item))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mains, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val personTRepository = PersonTRepository(this@DetailsActivity)

        return when (item.itemId) {
            R.id.action_sort_by_name -> {
                personTRepository.getPersonsSortedByName()?.let {
                    personTList.clear()
                    personTList.addAll(it)
                    viewDetailsAdapter.notifyDataSetChanged()
                }
                true
            }

            R.id.action_sort_by_age -> {
                personTRepository.getPersonsSortedByAge()?.let {
                    personTList.clear()
                    personTList.addAll(it)
                    viewDetailsAdapter.notifyDataSetChanged()
                }
                true
            }

            R.id.action_sort_by_city -> {
                personTRepository.getPersonsSortedByCity()?.let {
                    personTList.clear()
                    personTList.addAll(it)
                    viewDetailsAdapter.notifyDataSetChanged()
                }
                true
            }

            R.id.action_log_out -> {
                confirmDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun confirmDialog() {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        dialog.dismiss()
                        finish()
                    }

                    DialogInterface.BUTTON_NEGATIVE ->
                        //No button clicked
                        dialog.dismiss()
                }
            }
        val builder = AlertDialog.Builder(this@DetailsActivity)
        builder.setMessage(getString(R.string.do_you_wish_to_logout))
            .setPositiveButton(getString(R.string.yes), dialogClickListener)
            .setNegativeButton(getString(R.string.no), dialogClickListener)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackPressedDispatcher.onBackPressed()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
        return true
    }
}