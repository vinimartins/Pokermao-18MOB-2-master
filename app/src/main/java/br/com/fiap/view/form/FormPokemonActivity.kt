package br.com.fiap.view.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import br.com.fiap.R
import br.com.fiap.model.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_form_pokemon.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class FormPokemonActivity : AppCompatActivity() {

    val formPokemonViewModel: FormPokemonViewModel by viewModel()
    val baseURL : String by inject(named("baseURL"))
    val picasso: Picasso by inject()

    lateinit var pokemon:Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_pokemon)

        setValues()

    }

    private fun setValues() {

        pokemon = intent.getParcelableExtra("POKEMON")

        tvPokemonNameForm.text = pokemon.name
        picasso.load("$baseURL${pokemon.imageURL}").into(ivPokemonForm)

        setSeekBar(sbAttack,tvAttackValue, pokemon.attack)
        setSeekBar(sbDefense, tvDefenseValue, pokemon.defense)
        setSeekBar(sbVelocity, tvVelocityValue, pokemon.velocity)
        setSeekBar(sbPS, tvPSValue, pokemon.ps)

    }

    private fun setSeekBar(seekBar: SeekBar, textView: TextView, value:Int) {
        seekBar.progress = value
        textView.text = value.toString()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}


