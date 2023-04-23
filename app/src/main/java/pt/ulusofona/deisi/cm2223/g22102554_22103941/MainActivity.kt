package pt.ulusofona.deisi.cm2223.g22102554_22103941



import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import android.widget.AutoCompleteTextView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.ActivityMainBinding
import android.widget.ArrayAdapter
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var rBar: RatingBar? = null
    private var tView: TextView? = null
    private var textViewYourCurrentRating: TextView? = null
    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if(!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashboardFragment(supportFragmentManager)

        }

        binding.vozButton.setOnClickListener {

            object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Calcular o tempo restante em segundos.
                    val seconds = millisUntilFinished / 1000
                    // Exibir o Toast com o tempo restante em segundos.
                    Toast.makeText(applicationContext, "${getString(R.string.voice)} $seconds", Toast.LENGTH_SHORT).show()
                }

                override fun onFinish() {

                }
            }.start()
        }
        /*if (savedInstanceState == null) {
            NavigationManager.goToListaFilmesFragment(supportFragmentManager)
        } else {
            isDataLoaded = savedInstanceState.getBoolean("isDataLoaded")
            // Use the value of isDataLoaded to restore the state of the fragment
        }*/

    }


    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isDataLoaded", isDataLoaded)
    }*/

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
        //NavigationManager.goToDashboardFragment(supportFragmentManager)



    }

    private fun setupDrawerMenu() {
        val toggle = ActionBarDrawerToggle(this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener{
            onClickNavigationItem(it)
        }
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_registo ->NavigationManager.goToRegistoFilmesFragment(supportFragmentManager)
            R.id.nav_lista -> NavigationManager.goToListaFilmesFragment(supportFragmentManager)
            R.id.nav_mapa -> NavigationManager.goToMapaFragment(supportFragmentManager)
            R.id.nav_dashboard -> NavigationManager.goToDashboardFragment(supportFragmentManager)
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }


    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }




}