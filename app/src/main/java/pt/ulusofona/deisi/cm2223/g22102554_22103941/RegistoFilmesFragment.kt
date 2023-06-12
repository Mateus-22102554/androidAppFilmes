package pt.ulusofona.deisi.cm2223.g22102554_22103941


//import com.google.firebase.firestore.auth.User
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Repository
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentRegistoFilmesBinding
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Operacoes
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Cinema
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme
import java.io.InputStream


class RegistoFilmesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentRegistoFilmesBinding
    private val model = Repository.getInstance()

    private lateinit var photoFile: File
    private lateinit var adapterCinemas: ArrayAdapter<String>
    private lateinit var operacoes: Operacoes
    private val listImg = mutableListOf<File>()

    val listImgGet get() = listImg.toList()

    val clearList = listImg.clear()

    fun imagemSet (imgFile: File){
        listImg.add(imgFile)
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        operacoes = Repository.getInstance()

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.registo_filme)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registo_filmes, container, false)
        binding = FragmentRegistoFilmesBinding.bind(view)


        //scroll bar avaliação do Filme
        binding.barAvaliacaoFilme.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, valor: Int, p2: Boolean) {
                binding.valorAvaliacaoFilme.text = valor.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })


        /*  adapterFilmes = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, *//*Filme.nomesFilmesGet()*//* operacoes.getAllAvaliacoesNomes { it.getOrNull() })
        binding.nomeFilme.setAdapter(adapterFilmes)*/

        CoroutineScope(Dispatchers.IO).launch {
            var cinemasNomes = listOf<String>()
            operacoes.getAllCinemasNomes {
                cinemasNomes = it.getOrNull()!!
            }
            CoroutineScope(Dispatchers.Main).launch {
                adapterCinemas = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    cinemasNomes
                )
                binding.cinemaFilme.setAdapter(adapterCinemas)

            }
        }
        //adapterCinemas = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, operacoes.getAllCinemasNomes { it.getOrNull() })
        //binding.cinemaFilme.setAdapter(adapterCinemas)


        //DATA

        //captura os dados do calendario
        var calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val day = calendario.get(Calendar.DAY_OF_MONTH)

        binding.buttonData.setOnClickListener {
            //abre a janela da data (PickerDialog)

            DatePickerDialog(
                this.requireContext(),
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->

                    binding.dataSelect.text =
                        "$myear/${mmonth + 1}/$mday" //months começa do 0, dai adicionar +1
                    Log.i("", "Ano: $myear | Mes: $mmonth | Dia: $mday")

                    calendario.set(myear, mmonth, mday)

                    Log.i("", calendario.time.toString())


                },
                year,
                month,
                day
            ).show()

        }

        binding.buttonImg.setOnClickListener {

            obterCamera()

        }

        binding.buttonSubmit.setOnClickListener {

            val ocultarTeclado = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ocultarTeclado.hideSoftInputFromWindow(requireView().windowToken,0)


            if (binding.nomeFilme.text.isEmpty()) {
                binding.nomeFilme.error = getString(R.string.erroOBG)
            }

            if (binding.cinemaFilme.text.isEmpty()) {
                binding.cinemaFilme.error = getString(R.string.erroOBG)
            }

            if (binding.dataSelect.text.isEmpty()) {
                Toast.makeText(context, getString(R.string.erroObgData), Toast.LENGTH_SHORT).show()
            } else if (calendario.after(Calendar.getInstance())) {
                Toast.makeText(
                    context,
                    getString(R.string.erroDataInf),
                    Toast.LENGTH_SHORT
                ).show()
                binding.dataSelect.text = ""
            }

            if (binding.nomeFilme.text.isNotEmpty() && binding.cinemaFilme.text.isNotEmpty() && calendario.before(
                    Calendar.getInstance()
                )
            ) {
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.confirRegisto))
                    .setMessage(getString(R.string.prentendeRegistar) + " ${binding.nomeFilme.text}?")
                    .setPositiveButton(R.string.confirmar,
                        DialogInterface.OnClickListener { dialog, which ->

                            val filme: String = binding.nomeFilme.text.toString()
                            val nomeCinema: String = binding.cinemaFilme.text.toString()
                            val avaliacaoFilme = binding.valorAvaliacaoFilme.text.toString().toInt()
                            val calendarioLong: Long = calendario.timeInMillis
                            val observacoes = binding.obs.text.toString()

                            CoroutineScope(Dispatchers.IO).launch {

                                model.getFilmeIMDB(filme) {
                                    if (it.isSuccess) {

                                        it.onSuccess {
                                            model.verificarFilme(it.nomeImdb) { existeFilme ->
                                                model.verificarCinema(nomeCinema) { existeCinema ->

                                                    if (existeFilme == 0) {
                                                        //o filme não foi avaliado

                                                        if (existeCinema > 0) {
                                                            //o cinema existe

                                                            val filmeSucesso =
                                                                Filme(
                                                                    it.id,
                                                                    it.nomeImdb,
                                                                    it.generoImdb,
                                                                    it.dataImdb,
                                                                    it.avaliacaoImdb,
                                                                    it.imgImdb,
                                                                    it.sinopse
                                                                )

                                                            var cinema: Cinema
                                                            model.getCinemaByNome(nomeCinema) { resultCinema ->
                                                                resultCinema.onSuccess { cinemaSuccess ->
                                                                    cinema = Cinema(
                                                                        cinemaSuccess.cinema_id,
                                                                        cinemaSuccess.cinema_name,
                                                                        cinemaSuccess.latitude,
                                                                        cinemaSuccess.longitude,
                                                                        cinemaSuccess.morada,
                                                                        cinemaSuccess.localidade
                                                                    )

                                                                    val avaliacao = Avaliacao(
                                                                        UUID.randomUUID()
                                                                            .toString(),
                                                                        filmeSucesso,
                                                                        cinema,
                                                                        avaliacaoFilme,
                                                                        calendarioLong,
                                                                        null,
                                                                        observacoes
                                                                    )
                                                                    model.inserirAvaliacao(
                                                                        filmeSucesso,
                                                                        avaliacao
                                                                    ) { result ->
                                                                        if (result.isSuccess) {
                                                                            NavigationManager.goToListaFilmesFragment(
                                                                                parentFragmentManager
                                                                            )
                                                                        } else {
                                                                            Toast.makeText(
                                                                                requireContext(),
                                                                                result.exceptionOrNull()?.message,
                                                                                Toast.LENGTH_LONG
                                                                            ).show()
                                                                        }
                                                                    }

                                                                    model.inserirFotosAvaliacao(listImgGet, avaliacao.id) {
                                                                        clearList
                                                                    }

                                                                }
                                                            }
                                                        } else {
                                                            CoroutineScope(Dispatchers.Main).launch {
                                                                Toast.makeText(
                                                                    context,
                                                                    getString(R.string.erroCinemaNaoExiste),
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                    } else {
                                                        CoroutineScope(Dispatchers.Main).launch {
                                                            Toast.makeText(
                                                                context,
                                                                getString(R.string.erroFilmeAv),
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }

                                                    }
                                                }
                                            }


                                        }

                                    } else if (it.isFailure) {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            // Apresenta o erro num Toast
                                            Toast.makeText(
                                                requireContext(),
                                                getString(R.string.semInternet),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            // Apresenta o erro num Toast
                                            Toast.makeText(
                                                requireContext(),
                                                getString(R.string.erroFilmeNaoExiste),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }

                                    }

                                }

                            }

                            //Log.i("", Filmes.getAvaliacao.toString())


                            if (0 == 1) {

                                binding.nomeFilme.error = getString(R.string.erroFilmeNaoExiste)
                            } else if (0 == 3) {
                                binding.cinemaFilme.error = getString(R.string.erroCinemaNaoExiste)
                            } else {
                                binding.nomeFilme.text.clear()
                                binding.cinemaFilme.text.clear()
                                binding.barAvaliacaoFilme.progress = 1
                                binding.valorAvaliacaoFilme.text = "1"
                                calendario = Calendar.getInstance()
                                binding.dataSelect.text = ""
                                binding.obs.text.clear()
                                binding.imgsCount.text = ""
                                binding.img.setImageResource(0)
                                //FilmesParte1.imagensListClear()
                            }
                            getString(R.string.registo_filme)

                        })
                    .setNegativeButton(getString(R.string.cancelar), null)
                    .show()
            }
        }

        return binding.root
    }

    private fun obterCamera() {
        //criamos uma intenção para escolher uma imagem que está na parte interna do dispositivo (INTERNAL_CONTENT_URI)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Criar o arquivo onde a foto será armazenada

        photoFile = createImageFile()
        val photoURI = FileProvider.getUriForFile(
            requireContext(),
            "pt.ulusofona.deisi.cm2223.g22102554_22103941.fileprovider",
            photoFile
        )

        // Passar o URI para a câmera capturar a foto e salvar no arquivo
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

        //utilizamos a intenção para fazer um pedido a galeria, onde escolhemos a imagem, criamos o codigo de comunicação 11 que fica para estabelecer a comunicação com a galeria

        activityResultLauncher.launch(intent)

    }

    var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Obtenha a URI da imagem capturada usando o caminho do arquivo
                val uri = Uri.fromFile(photoFile)

                // Use a URI para exibir a imagem capturada
                binding.img.setImageURI(uri)
                imagemSet(photoFile)
                binding.imgsCount.text = listImgGet.size.toString()
            }
        }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }


}

