package pt.ulusofona.deisi.cm2223.g22102554_22103941

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
//import pt.ulusofona.deisi.cm2223.RegistoFilmesFragment.RegistoFilmesFragment

object NavigationManager {
    private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
        val transition = fm.beginTransaction()
        transition.replace(R.id.frame, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }

    fun goToRegistoFilmesFragment(fm: FragmentManager) {
        placeFragment(fm, RegistoFilmesFragment())
    }
    fun goToListaFilmesFragment(fm: FragmentManager) {
        placeFragment(fm, ApresentacaoFilmesFragment())
    }

    fun goToDetalheFilmeFragment(fm: FragmentManager, filme: Filme) {
        placeFragment(fm, DetalheFilmeFragment(filme))
    }

    fun goToMapaFragment(fm: FragmentManager) {
        placeFragment(fm, MapaFragment())
    }

    fun goToDashboardFragment(fm: FragmentManager) {
        placeFragment(fm, DashboardFragment())
    }


}