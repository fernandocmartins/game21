package com.example.game21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import br.com.fiap.mobcomponents.customtoast.CustomToast
import com.fernando.domain.model.Carta
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btRecomecar: Button
    private lateinit var btProximaCarta: Button
    private lateinit var tvPontuacao: TextView
    private lateinit var ivCarta: ImageView
    private var cartas: MutableList<Carta> = mutableListOf()
    private val gerador = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpView()
        setListeners()
        iniciarPartida()
    }

    private fun iniciarPartida() {
        tvPontuacao.text = "0"
        cartas = getBaralho()
        ivCarta.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo))
    }

    private fun realizarJogada() {
        val posicaoCartaSelecionada = gerador.nextInt(cartas.size)
        val cartaSelecionada = cartas.get(posicaoCartaSelecionada)
        val pontuacaoAtualizada = tvPontuacao.text.toString().toInt() +
                cartaSelecionada.points
        tvPontuacao.text = pontuacaoAtualizada.toString()
        exibeMensagem(pontuacaoAtualizada)
        if (pontuacaoAtualizada > 21) {
            iniciarPartida()
        } else {
            cartas.removeAt(posicaoCartaSelecionada)
            ivCarta.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    cartaSelecionada.resourceId
                )
            )
        }
    }

    private fun controlarBotoes() {
        val time = Toast.LENGTH_LONG
        habilitarBotoes(false)
        Handler().postDelayed({
            habilitarBotoes(true)
        }, 2500)
    }

    private fun exibeMensagem (pontuacao: Int) {
        when {
            pontuacao == 21 -> {
                CustomToast.success( this, "Você atingiu a melhor pontuação. Hora de parar :)" )
            }
            pontuacao > 21 -> {
                CustomToast.error(this, "Você perdeu fez $pontuacao e perdeu" )
            }
            pontuacao > 11 -> {
                CustomToast.warning(this,
                    "Cuidado, dependendo da carta que comprar você poderá perder"
                )
            }
            else -> {
                CustomToast.info(this, "Você ainda pode jogar com segurança" )
            }
        }
        controlarBotoes()
    }


    private fun habilitarBotoes(habilitar: Boolean) {
        btProximaCarta.isEnabled = habilitar
        btRecomecar.isEnabled = habilitar
    }

    private fun setListeners() {
        btProximaCarta.setOnClickListener {
            realizarJogada()
        }
        btRecomecar.setOnClickListener {
            iniciarPartida()
        }
    }

    private fun setUpView() {
        btRecomecar = findViewById(R.id.btRecomecar)
        btProximaCarta = findViewById(R.id.btProximaCarta)
        tvPontuacao = findViewById(R.id.tvPontuacao)
        ivCarta = findViewById(R.id.ivCarta)
    }

    private fun getBaralho () : MutableList<Carta> {
        return mutableListOf<Carta>(
            Carta(R.drawable.as_de_espada, 1),
            Carta(R.drawable.dois_de_espada, 2),
            Carta(R.drawable.tres_de_espada, 3),
            Carta(R.drawable.quatro_de_espada, 4),
            Carta(R.drawable.cinco_de_espada, 5),
            Carta(R.drawable.seis_de_espada, 6),
            Carta(R.drawable.sete_de_espada, 7),
            Carta(R.drawable.oito_de_espada, 8),
            Carta(R.drawable.nove_de_espada, 9),
            Carta(R.drawable.dez_de_espada, 10),
            Carta(R.drawable.valete_de_espada, 10),
            Carta(R.drawable.dama_de_espada, 10),
            Carta(R.drawable.rei_de_espada, 10)
        )
    }

}