package com.manuellugodev.movie.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.movie.R
import com.manuellugodev.movie.data.profile.RepositoryProfile
import com.manuellugodev.movie.firebase.sources.DataSourceProfileFirebase
import com.manuellugodev.movie.vo.DataResult
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    val db=FirebaseFirestore.getInstance()
    val dataSource= DataSourceProfileFirebase(db)
    val repository=RepositoryProfile(dataSource)

    private val profileViewModel by viewModels<ProfileViewModel> { ProfileViewModelFactory(repository) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.fetchProfileData.observe(viewLifecycleOwner, Observer {result->

            when(result){

                is DataResult.Loading ->
                    showProgress()
                is DataResult.Success ->{
                    val profile=result.data
                    hideProgress()
                    txtNameProfile.text="${profile.name} ${profile.lastname}"
                    textEmail.text=profile.email
                    textCountry.text=profile.country
                    textPreferences.text=profile.preferences
                }

                is DataResult.Failure ->{
                    hideProgress()
                    showMessage(result.exception.message.toString())
                }
            }


        })
    }

    private fun showProgress(){

        progressProfile.visibility=View.VISIBLE
    }

    private fun hideProgress(){

        progressProfile.visibility=View.GONE
    }

    private fun showMessage(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }
}