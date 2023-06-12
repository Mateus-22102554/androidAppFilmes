package pt.ulusofona.deisi.cm2223.g22102554_22103941


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
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
import com.fondesa.kpermissions.extension.permissionsBuilder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Repository
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Operacoes
import pt.ulusofona.deisi.cm2223.g22102554_22103941.MapaFragment
import java.util.*
import android.Manifest
import android.app.Activity
import android.speech.RecognizerIntent
import android.speech.RecognizerIntent.EXTRA_RESULTS
import android.speech.SpeechRecognizer
import android.text.Layout
import android.view.LayoutInflater
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.PopVozBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var rBar: RatingBar? = null
    private var tView: TextView? = null
    private var textViewYourCurrentRating: TextView? = null
    private var isDataLoaded = false
    private lateinit var model: Operacoes
    private lateinit var speechRecognizer: SpeechRecognizer
    private val REQUEST_CODE_SPEECH = 100
    private var pesquisaVoz = ""
    private lateinit var bindingVoz: PopVozBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).build().send { result ->
            if (result.allGranted()) {
                // Este if já cá estava antes, para garantir que ficamos no
                // ecrã em caso de ocorrer uma rotação
                if (!screenRotated(savedInstanceState)) {
                    NavigationManager.goToDashboardFragment(
                        supportFragmentManager
                    )
                }
            } else {
                finish()
            }
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)




        model = Repository.getInstance()

        /*if (!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashboardFragment(supportFragmentManager)

        }*/



        binding.vozButton.setOnClickListener {

            bindingVoz = PopVozBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(this)
                .setView(bindingVoz.root)
                .create()
            dialog.show()

            bindingVoz.voiceDetect.setOnClickListener {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )

                // Iniciar a atividade de reconhecimento de fala
                startActivityForResult(intent, REQUEST_CODE_SPEECH)

            }

            bindingVoz.voiceConfirm.setOnClickListener {
                if (pesquisaVoz != "") {
                    CoroutineScope(Dispatchers.IO).launch {
                        model.getAvaliacaoIdFromFilmeName(pesquisaVoz) {
                            val idPesquisa = it.getOrNull()

                            if (idPesquisa != null) {
                                NavigationManager.goToDetalheFilmeFragment(supportFragmentManager, idPesquisa)
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Esse filme ainda não foi avaliado",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Clique no botão \"Falar",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }
            CoroutineScope(Dispatchers.IO).launch {
                // call getCharacters on the "IO Thread"
                model.getCinemasJSON { it.getOrNull() }
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayExtra(EXTRA_RESULTS)
            val textoVoz = result?.get(0)
            if (textoVoz != null) {
                pesquisaVoz = textoVoz
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()


        //NavigationManager.goToDashboardFragment(supportFragmentManager)


    }

    private fun setupDrawerMenu() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener {
            onClickNavigationItem(it)
        }
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_registo -> NavigationManager.goToRegistoFilmesFragment(supportFragmentManager)
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