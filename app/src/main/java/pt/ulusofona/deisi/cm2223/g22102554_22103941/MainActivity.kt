package pt.ulusofona.deisi.cm2223.g22102554_22103941



import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
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
import androidx.appcompat.app.AlertDialog
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



        if (!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashboardFragment(supportFragmentManager)

        }

        binding.vozButton.setOnClickListener {


            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.voice)

            val popupText = TextView(this)
            popupText.textSize = 30f
            popupText.gravity = Gravity.CENTER
            builder.setView(popupText)

            val alert = builder.create()
            alert.show()

            // Calcular o tempo restante em segundos.
            object : CountDownTimer(10000, 1000) {
                override fun onTick(milisSegundos: Long) {
                    popupText.text = "${milisSegundos / 1000}"
                }

                override fun onFinish() {
                    alert.dismiss()
                }

            }.start()
        }
    }

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