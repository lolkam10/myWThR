package com.example.mywthr.View

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mywthr.MainActivity
import com.example.mywthr.R
import com.example.mywthr.ViewModel.VM
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class MainPageFragment(var ifOld: Boolean) : Fragment() {

    lateinit var viewModel:VM

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        if (ifOld){
            view = inflater.inflate(R.layout.main_paige_old,container,false)
        }else{
            view = inflater.inflate(R.layout.main_page,container,false)
        }

        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(VM::class.java)

        viewModel.all_data.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.mywthr_city).text = it.name
            view.findViewById<TextView>(R.id.mywthr_humidity).text = it.main.humidity.toString()+" %"
            view.findViewById<TextView>(R.id.mywthr_pressure).text = it.main.pressure.toString()+" hPa"
            view.findViewById<TextView>(R.id.mywthr_description).text = it.weather[0].description
            view.findViewById<TextView>(R.id.mywthr_date).text = viewModel.convertDate(it.dt,it.timezone)[0]
            view.findViewById<TextView>(R.id.mywthr_sunrise).text = viewModel.convertDate(it.sys.sunrise,it.timezone)[1]
            view.findViewById<TextView>(R.id.mywthr_sunset).text = viewModel.convertDate(it.sys.sunset,it.timezone)[1]
            view.findViewById<TextView>(R.id.mywthr_temp).text = it.main.temp.roundToInt().toString()+"\u2103"
            Picasso.get().load("https://openweathermap.org/img/wn/"+it.weather[0].icon+"@2x.png").into(
                view.findViewById<ImageView>(R.id.mywthr_icon))
        }
        viewModel.getDataForCity("Warszawa")
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.top_bar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)

        val searchView : SearchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.queryHint = "Wyszukaj miasto"
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.search -> {
            val searchView : SearchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.getDataForCity(query?:"Katowice")
                    //Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()
                    searchView.onActionViewCollapsed()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            true
        }

        R.id.change_layout -> {
            //Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()
            parentFragmentManager.beginTransaction().replace(R.id.main_container,MainPageFragment(!ifOld)).commit()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}


