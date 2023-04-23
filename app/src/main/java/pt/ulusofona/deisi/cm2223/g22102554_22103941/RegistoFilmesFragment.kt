package pt.ulusofona.deisi.cm2223.g22102554_22103941


//import com.google.firebase.firestore.auth.User
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentRegistoFilmesBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class RegistoFilmesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentRegistoFilmesBinding
    private lateinit var dateButton: Button
    private lateinit var dataText: TextView
    private lateinit var photoFile: File
    private lateinit var adapterFilmes: ArrayAdapter<String>
    private lateinit var adapterCinemas: ArrayAdapter<String>


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



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

        adapterFilmes = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, FilmesIMDB.nomesFilmesGet())
        binding.nomeFilme.setAdapter(adapterFilmes)
        adapterCinemas = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, Cinemas.nomesCinemasGet())
        binding.cinemaFilme.setAdapter(adapterCinemas)


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

            if (binding.nomeFilme.text.isEmpty()) {
                binding.nomeFilme.error = "Campo obrigatório!"
            }

            if (binding.cinemaFilme.text.isEmpty()) {
                binding.cinemaFilme.error = "Campo obrigatório!"
            }

            if (binding.dataSelect.text.isEmpty()) {
                Toast.makeText(context, "O campo data é obrigatório", Toast.LENGTH_SHORT).show()
            } else if (calendario.after(Calendar.getInstance())) {
                Toast.makeText(
                    context,
                    "A data tem de ser inferior à data atual",
                    Toast.LENGTH_SHORT
                ).show()
                binding.dataSelect.text = ""
            }

            if (binding.nomeFilme.text.isNotEmpty() && binding.cinemaFilme.text.isNotEmpty() && calendario.before(
                    Calendar.getInstance()
                )
            ) {
                AlertDialog.Builder(context)
                    .setTitle("Confirmar Registo")
                    .setMessage("Pretende registar o filme ${binding.nomeFilme.text}?")
                    .setPositiveButton("Confirmar",
                        DialogInterface.OnClickListener { dialog, which ->

                            val result = Filmes.historySet(
                                binding.nomeFilme.text.toString(),
                                binding.cinemaFilme.text.toString(),
                                binding.valorAvaliacaoFilme.text.toString().toInt(),
                                calendario,
                                Filmes.listImgGet,
                                binding.obs.text.toString()
                            )

                            if (result == 2) {

                                Toast.makeText(
                                    context,
                                    "Erro: Este filme já foi avaliado!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else if (result == 1) {
                                Log.i("", "ola")
                                binding.nomeFilme.error = "Filme Não Existe"
                            } else if (result == 3) {
                                binding.cinemaFilme.error = "Não Existe o Cinema"
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
                                Filmes.imagensListClear()
                            }
                            getString(R.string.registo_filme)

                        })
                    .setNegativeButton("Cancelar", null)
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
                Filmes.imagemSet(photoFile)
                binding.imgsCount.text = Filmes.listImgGet.size.toString()
            }
        }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }


}

