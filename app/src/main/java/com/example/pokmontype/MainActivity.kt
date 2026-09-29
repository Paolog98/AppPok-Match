package com.example.pokmontype

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokmontype.ui.theme.PokèmonTypeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        val buttonNormale = findViewById<Button>(R.id.button_normale)
        val buttonFuoco = findViewById<Button>(R.id.button_fuoco)
        val buttonAcqua = findViewById<Button>(R.id.button_acqua)
        val buttonErba = findViewById<Button>(R.id.button_erba)
        val buttonElettro = findViewById<Button>(R.id.button_elettro)
        val buttonGhiaccio = findViewById<Button>(R.id.button_ghiaccio)
        val buttonLotta = findViewById<Button>(R.id.button_lotta)
        val buttonVeleno = findViewById<Button>(R.id.button_veleno)
        val buttonTerra = findViewById<Button>(R.id.button_terra)
        val buttonVolante = findViewById<Button>(R.id.button_volante)
        val buttonPsico = findViewById<Button>(R.id.button_psico)
        val buttonColeottero = findViewById<Button>(R.id.button_coleottero)
        val buttonRoccia = findViewById<Button>(R.id.button_roccia)
        val buttonSpettro = findViewById<Button>(R.id.button_spettro)
        val buttonDrago = findViewById<Button>(R.id.button_drago)
        val buttonBuio = findViewById<Button>(R.id.button_buio)
        val buttonAcciaio = findViewById<Button>(R.id.button_acciaio)
        val buttonFolletto = findViewById<Button>(R.id.button_folletto)
        val textMessaggio = findViewById<TextView>(R.id.textMessaggio)


        // Classe Tipo
        data class Tipo(
            val superEff: List<String>,   // x2
            val pocoEff: List<String>,    // ½
            val immune: List<String> = emptyList() // x0
        )
        // Mappa tipo -> debolezze e resistenze

        val tipoInfo: Map<String, Tipo> = mapOf(

            "Normale" to Tipo(
                superEff = listOf(),
                pocoEff = listOf("Roccia", "Acciaio"),
                immune = listOf("Spettro")
            ),

            "Fuoco" to Tipo(
                superEff = listOf("Erba", "Ghiaccio", "Coleottero", "Acciaio"),
                pocoEff = listOf("Fuoco", "Acqua", "Roccia", "Drago")
            ),

            "Acqua" to Tipo(
                superEff = listOf("Fuoco", "Terra", "Roccia"),
                pocoEff = listOf("Acqua", "Erba", "Drago")
            ),

            "Elettro" to Tipo(
                superEff = listOf("Acqua", "Volante"),
                pocoEff = listOf("Elettro", "Erba", "Drago"),
                immune = listOf("Terra")
            ),

            "Erba" to Tipo(
                superEff = listOf("Acqua", "Terra", "Roccia"),
                pocoEff = listOf("Fuoco", "Erba", "Veleno", "Volante", "Coleottero", "Drago", "Acciaio")
            ),

            "Ghiaccio" to Tipo(
                superEff = listOf("Erba", "Terra", "Volante", "Drago"),
                pocoEff = listOf("Fuoco", "Acqua", "Ghiaccio", "Acciaio")
            ),

            "Lotta" to Tipo(
                superEff = listOf("Normale", "Ghiaccio", "Roccia", "Buio", "Acciaio"),
                pocoEff = listOf("Veleno", "Volante", "Psico", "Coleottero", "Folletto"),
                immune = listOf("Spettro")
            ),

            "Veleno" to Tipo(
                superEff = listOf("Erba", "Folletto"),
                pocoEff = listOf("Veleno", "Terra", "Roccia", "Spettro"),
                immune = listOf("Acciaio")
            ),

            "Terra" to Tipo(
                superEff = listOf("Fuoco", "Elettro", "Veleno", "Roccia", "Acciaio"),
                pocoEff = listOf("Erba", "Coleottero"),
                immune = listOf("Volante")
            ),

            "Volante" to Tipo(
                superEff = listOf("Erba", "Lotta", "Coleottero"),
                pocoEff = listOf("Elettro", "Roccia", "Acciaio")
            ),

            "Psico" to Tipo(
                superEff = listOf("Lotta", "Veleno"),
                pocoEff = listOf("Psico", "Acciaio"),
                immune = listOf("Buio")
            ),

            "Coleottero" to Tipo(
                superEff = listOf("Erba", "Psico", "Buio"),
                pocoEff = listOf("Fuoco", "Lotta", "Veleno", "Volante", "Spettro", "Acciaio", "Folletto")
            ),

            "Roccia" to Tipo(
                superEff = listOf("Fuoco", "Ghiaccio", "Volante", "Coleottero"),
                pocoEff = listOf("Lotta", "Terra", "Acciaio")
            ),

            "Spettro" to Tipo(
                superEff = listOf("Psico", "Spettro"),
                pocoEff = listOf("Buio"),
                immune = listOf("Normale")
            ),

            "Drago" to Tipo(
                superEff = listOf("Drago"),
                pocoEff = listOf("Acciaio"),
                immune = listOf("Folletto")
            ),

            "Buio" to Tipo(
                superEff = listOf("Psico", "Spettro"),
                pocoEff = listOf("Lotta", "Buio", "Folletto")
            ),

            "Acciaio" to Tipo(
                superEff = listOf("Ghiaccio", "Roccia", "Folletto"),
                pocoEff = listOf("Fuoco", "Acqua", "Elettro", "Acciaio")
            ),

            "Folletto" to Tipo(
                superEff = listOf("Lotta", "Drago", "Buio"),
                pocoEff = listOf("Fuoco", "Veleno", "Acciaio")
            )

        )
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val listView = findViewById<ListView>(R.id.listView)






        // Bottone Normale
        buttonNormale.setOnClickListener {
            val info = tipoInfo["Normale"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonNormale.setTextColor(getColor(android.R.color.system_accent1_100))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br>Normale⚪️:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                         "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
          searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Normale) - BST: 720",
                "Slaking - BST: 670",
                "Regigigas - BST: 670",
                "Silvally (Normale) - BST: 570",
                "Snorlax - BST: 540",
                "Blissey - BST: 540",
                "Porygon-Z - BST: 535",
                "Type: Null - BST: 534",
                "Dudunsparce - BST: 520",
                "Lickilicky - BST: 515",
                "Porygon2 - BST: 515",
                "Ursaring - BST: 500",
                "Stoutland - BST: 500",
                "Bewear - BST: 500",
                "Kangaskhan - BST: 490",
                "Tauros - BST: 490",
                "Miltank - BST: 490",
                "Exploud - BST: 490",
                "Bouffalant - BST: 490",
                "Dubwool - BST: 490",
                "Ambipom - BST: 482",
                "Komala - BST: 480",
                "Lopunny - BST: 480",
                "Furfrou - BST: 472",
                "Maushold - BST: 470",
                "Greedent - BST: 460",
                "Zangoose - BST: 458",
                "Purugly - BST: 452",
                "Terapagos - BST: 450",
                "Audino - BST: 445"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })


        }


// Bottone Fuoco
        buttonFuoco.setOnClickListener {
            val info = tipoInfo["Fuoco"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonFuoco.setTextColor(getColor(android.R.color.holo_red_dark))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Fuoco🔥:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Fuoco) - BST: 720",
                "Entei - BST: 580",
                "Silvally (Fuoco) - BST: 570",
                "Arcanine - BST: 555",
                "Typhlosion - BST: 534",
                "Cinderace - BST: 530",
                "Flareon - BST: 525",
                "Ninetales - BST: 505",
                "Rapidash - BST: 500",
                "Simisear - BST: 498",
                "Magmortar - BST: 495",
                "Magmar - BST: 495",
                "Heatmor - BST: 484",
                "Darmanitan - BST: 480",
                "Torkoal - BST: 470",
                "Castform (Forma Sole) - BST: 420",
                "Ponyta - BST: 410",
                "Braixen - BST: 409",
                "Charmeleon - BST: 405",
                "Quilava - BST: 405",
                "Raboot - BST: 405",
                "Torracat - BST: 405",
                "Growlithe - BST: 350",
                "Litten - BST: 320",
                "Pansear - BST: 316",
                "Darumaka - BST: 315",
                "Torchic - BST: 310",
                "Scorbunny - BST: 310",
                "Fuecoco - BST: 310",
                "Chimchar - BST: 309"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })



        }

// Bottone Acqua
        buttonAcqua.setOnClickListener {
            val info = tipoInfo["Acqua"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonAcqua.setTextColor(getColor(android.R.color.holo_blue_light))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Acqua💧:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Acqua) - BST: 720",
                "Kyogre - BST: 670",
                "Manaphy - BST: 600",
                "Palafin (Forma Eroe) - BST: 650",
                "Suicune - BST: 580",
                "Silvally (Acqua) - BST: 570",
                "Milotic - BST: 540",
                "Dondozo - BST: 530",
                "Blastoise - BST: 530",
                "Feraligatr - BST: 530",
                "Inteleon - BST: 528",
                "Samurott - BST: 528",
                "Vaporeon - BST: 525",
                "Wailord - BST: 500",
                "Simipour - BST: 498",
                "Golduck - BST: 500",
                "Huntail - BST: 485",
                "Gorebyss - BST: 485",
                "Floatzel - BST: 495",
                "Barraskewda - BST: 490",
                "Octillery - BST: 480",
                "Kingler - BST: 475",
                "Alomomola - BST: 470",
                "Basculin - BST: 460",
                "Lumineon - BST: 460",
                "Seaking - BST: 450",
                "Wugtrio - BST: 425",
                "Poliwhirl - BST: 385",
                "Staryu - BST: 340",
                "Clamperl - BST: 345"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })


        }

// Bottone Erba
        buttonErba.setOnClickListener {
            val info = tipoInfo["Erba"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonErba.setTextColor(getColor(android.R.color.holo_green_dark))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Erba🌿:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Erba) - BST: 720",
                "Shaymin - BST: 600",
                "Silvally (Erba) - BST: 570",
                "Ogerpon (Maschera Turchese) - BST: 550",
                "Tangrowth - BST: 535",
                "Gogoat - BST: 531",
                "Rillaboom - BST: 530",
                "Sceptile - BST: 530",
                "Serperior - BST: 528",
                "Meganium - BST: 525",
                "Leafeon - BST: 525",
                "Tsareena - BST: 510",
                "Arboliva - BST: 510",
                "Simisage - BST: 498",
                "Bellossom - BST: 490",
                "Lilligant - BST: 480",
                "Lurantis - BST: 480",
                "Maractus - BST: 461",
                "Eldegoss - BST: 460",
                "Carnivine - BST: 454",
                "Cherrim - BST: 450",
                "Tangela - BST: 435",
                "Sunflora - BST: 425",
                "Thwackey - BST: 420",
                "Servine - BST: 413",
                "Floragato - BST: 406",
                "Bayleef - BST: 405",
                "Quilladin - BST: 405",
                "Grovyle - BST: 405",
                "Treecko - BST: 310"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Elettro
        buttonElettro.setOnClickListener {
            val info = tipoInfo["Elettro"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonElettro.setTextColor(getColor(android.R.color.holo_orange_light))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Elettro⚡️:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Elettro) - BST: 720",
                "Zeraora - BST: 600",
                "Raikou - BST: 580",
                "Regieleki - BST: 580",
                "Xurkitree - BST: 570",
                "Silvally (Elettro) - BST: 570",
                "Electivire - BST: 540",
                "Jolteon - BST: 525",
                "Luxray - BST: 523",
                "Eelektross - BST: 515",
                "Ampharos - BST: 510",
                "Zebstrika - BST: 497",
                "Bellibolt - BST: 495",
                "Electabuzz - BST: 490",
                "Electrode - BST: 490",
                "Boltund - BST: 490",
                "Raichu - BST: 485",
                "Manectric - BST: 475",
                "Pincurchin - BST: 435",
                "Pachirisu - BST: 405",
                "Plusle - BST: 405",
                "Minun - BST: 405",
                "Eelektrik - BST: 405",
                "Luxio - BST: 363",
                "Flaaffy - BST: 365",
                "Elekid - BST: 360",
                "Voltorb - BST: 330",
                "Pikachu - BST: 320",
                "Blitzle - BST: 295",
                "Electrike - BST: 295"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Ghiaccio
        buttonGhiaccio.setOnClickListener {
            val info = tipoInfo["Ghiaccio"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonGhiaccio.setTextColor(getColor(android.R.color.holo_blue_bright))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))


            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br> Ghiaccio❄️<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Ghiaccio) - BST: 720",
                "Glastrier - BST: 580",
                "Regice - BST: 580",
                "Silvally (Ghiaccio) - BST: 570",
                "Vanilluxe - BST: 535",
                "Glaceon - BST: 525",
                "Cetitan - BST: 521",
                "Cryogonal - BST: 515",
                "Avalugg - BST: 514",
                "Beartic - BST: 505",
                "Darmanitan (Galar) - BST: 480",
                "Glalie - BST: 480",
                "Eiscue - BST: 470",
                "Castform (Forma Neve) - BST: 420",
                "Vanillish - BST: 395",
                "Cetoddle - BST: 334",
                "Cubchoo - BST: 305",
                "Vanillite - BST: 305",
                "Bergmite - BST: 304",
                "Snorunt - BST: 300"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Lotta
        buttonLotta.setOnClickListener {
            val info = tipoInfo["Lotta"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonLotta.setTextColor(getColor(android.R.color.system_error_400))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br> Lotta🥊:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Lotta) - BST: 720",
                "Zamazenta (Hero of Many Battles) - BST: 670",
                "Silvally (Lotta) - BST: 570",
                "Mienshao - BST: 510",
                "Sirfetch'd - BST: 507",
                "Machamp - BST: 505",
                "Conkeldurr - BST: 505",
                "Passimian - BST: 490",
                "Tauros di Paldea (Lotta) - BST: 490",
                "Grapploct - BST: 480",
                "Hariyama - BST: 474",
                "Falinks - BST: 470",
                "Throh - BST: 465",
                "Sawk - BST: 465",
                "Primeape - BST: 455",
                "Hitmonlee - BST: 455",
                "Hitmonchan - BST: 455",
                "Hitmontop - BST: 455",
                "Machoke - BST: 405",
                "Gurdurr - BST: 405",
                "Kubfu - BST: 385",
                "Mienfoo - BST: 350",
                "Pancham - BST: 348",
                "Crabrawler - BST: 338",
                "Machop - BST: 305",
                "Mankey - BST: 305",
                "Timburr - BST: 305",
                "Riolu - BST: 285",
                "Makuhita - BST: 237",
                "Tyrogue - BST: 210"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Veleno
        buttonVeleno.setOnClickListener {
            val info = tipoInfo["Veleno"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonVeleno.setTextColor(getColor(android.R.color.system_accent3_600))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Veleno☠️:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Veleno) - BST: 720",
                "Silvally (Veleno) - BST: 570",
                "Muk - BST: 500",
                "Weezing - BST: 490",
                "Garbodor - BST: 474",
                "Swalot - BST: 467",
                "Seviper - BST: 458",
                "Arbok - BST: 448",
                "Koffing - BST: 340",
                "Trubbish - BST: 329",
                "Grimer - BST: 325",
                "Gulpin - BST: 300",
                "Ekans - BST: 288"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Terra
        buttonTerra.setOnClickListener {
            val info = tipoInfo["Terra"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonTerra.setTextColor(getColor(android.R.color.holo_orange_dark))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Terra🌎:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Terra) - BST: 720",
                "Groudon - BST: 670",
                "Silvally (Terra) - BST: 570",
                "Hippowdon - BST: 525",
                "Sandaconda - BST: 510",
                "Donphan - BST: 500",
                "Mudsdale - BST: 500",
                "Sandslash - BST: 450",
                "Dugtrio - BST: 425",
                "Marowak - BST: 425",
                "Hippopotas - BST: 330",
                "Phanpy - BST: 330",
                "Drilbur - BST: 328",
                "Cubone - BST: 320",
                "Silicobra - BST: 315",
                "Sandshrew - BST: 300",
                "Trapinch - BST: 290",
                "Diglett - BST: 265"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Volante
        buttonVolante.setOnClickListener {
            val info = tipoInfo["Volante"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonVolante.setTextColor(getColor(android.R.color.system_primary_dark))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br> Volante🕊️:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus(Volante)  - BST: 720",
                "Tornadus (Incarnate Forme) - BST: 580",
                "Tornadus (Therian Forme) - BST: 580",
                "Silvally (Volante) - BST: 570",
                "Corvisquire - BST: 365",
                "Rookidee - BST: 245"
            )
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Psico
        buttonPsico.setOnClickListener {
            val info = tipoInfo["Psico"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonPsico.setTextColor(getColor((R.color.purple_200)))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))



            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Psico🔮:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Psico) - BST: 720",
                "Mewtwo - BST: 680",
                "Deoxys - BST: 600",
                "Mew - BST: 600",
                "Necrozma - BST: 600",
                "Cresselia - BST: 580",
                "Silvally (Psico) - BST: 570",
                "Espeon - BST: 525",
                "Alakazam - BST: 500",
                "Gothitelle - BST: 490",
                "Oranguru - BST: 490",
                "Reuniclus - BST: 490",
                "Musharna - BST: 487",
                "Beheeyem - BST: 485",
                "Hypno - BST: 483",
                "Espathra - BST: 481",
                "Grumpig - BST: 455",
                "Wobbuffet - BST: 405",
                "Galarian Ponyta - BST: 410",
                "Kadabra - BST: 400",
                "Gothorita - BST: 390",
                "Duosion - BST: 370",
                "Espurr - BST: 355",
                "Unown - BST: 336",
                "Spoink - BST: 330",
                "Drowzee - BST: 328",
                "Abra - BST: 310",
                "Galarian Slowpoke - BST: 315",
                "Munna - BST: 292",
                "Gothita - BST: 290"
            )


            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Coleottero
        buttonColeottero.setOnClickListener {
            val info = tipoInfo["Coleottero"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonColeottero.setTextColor(getColor(android.R.color.holo_green_light))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Coleottero🐛:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Coleottero) - BST: 720",
                "Silvally (Coleottero) - BST: 570",
                "Pinsir - BST: 500",
                "Accelgor - BST: 495",
                "Spidops - BST: 404",
                "Volbeat - BST: 400",
                "Illumise - BST: 400",
                "Kricketune - BST: 384",
                "Karrablast - BST: 315",
                "Shelmet - BST: 305",
                "Grubbin - BST: 300",
                "Rellor - BST: 295",
                "Pineco - BST: 290",
                "Burmy - BST: 224",
                "Spewpa - BST: 213",
                "Cascoon - BST: 205",
                "Silcoon - BST: 205",
                "Metapod - BST: 205",
                "Nymble - BST: 210",
                "Tarountula - BST: 210",
                "Scatterbug - BST: 200",
                "Caterpie - BST: 195",
                "Wurmple - BST: 195",
                "Kricketot - BST: 194",
                "Blipbug - BST: 180"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Roccia
        buttonRoccia.setOnClickListener {
            val info = tipoInfo["Roccia"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonRoccia.setTextColor(getColor(android.R.color.system_error_container_dark))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br> Roccia🪨:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Roccia) - BST: 720",
                "Regirock - BST: 580",
                "Silvally (Roccia) - BST: 570",
                "Gigalith - BST: 515",
                "Garganacl - BST: 500",
                "Stonjourner - BST: 470",
                "Lycanroc - BST: 487",
                "Rampardos - BST: 495",
                "Sudowoodo - BST: 410",
                "Klawf - BST: 450",
                "Boldore - BST: 390",
                "Nosepass - BST: 375",
                "Roggenrola - BST: 280",
                "Rockruff - BST: 280",
                "Cranidos - BST: 350",
                "Rolycoly - BST: 240",
                "Naclstack - BST: 355",
                "Bonsly - BST: 290",
                "Nacli - BST: 280"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Spettro
        buttonSpettro.setOnClickListener {
            val info = tipoInfo["Spettro"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonSpettro.setTextColor(getColor(R.color.purple_500))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))


            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI  <br> Spettro👻:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Spettro) - BST: 720",
                "Silvally (Spettro) - BST: 570",
                "Dusknoir - BST: 525",
                "Mismagius - BST: 495",
                "Cofagrigus - BST: 483",
                "Banette - BST: 455",
                "Dusclops - BST: 455",
                "Misdreavus - BST: 435",
                "Corsola di Galar - BST: 410",
                "Sinistea - BST: 308",
                "Yamask - BST: 303",
                "Shuppet - BST: 295",
                "Duskull - BST: 295"
            )
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Drago
        buttonDrago.setOnClickListener {
            val info = tipoInfo["Drago"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonDrago.setTextColor(getColor(android.R.color.system_accent1_900))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br> Drago🐉:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Drago) - BST: 720",
                "Goodra - BST: 600",
                "Regidrago - BST: 580",
                "Silvally (Drago) - BST: 570",
                "Haxorus - BST: 540",
                "Druddigon - BST: 485",
                "Sliggoo - BST: 452",
                "Dragonair - BST: 420",
                "Shelgon - BST: 420",
                "Fraxure - BST: 410",
                "Axew - BST: 320",
                "Bagon - BST: 300",
                "Dratini - BST: 300",
                "Goomy - BST: 300",
                "Jangmo-o - BST: 300"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Buio
        buttonBuio.setOnClickListener {
            val info = tipoInfo["Buio"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonBuio.setTextColor(getColor(android.R.color.black))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br> Buio🌑:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Buio) - BST: 720",
                "Darkrai - BST: 600",
                "Silvally (Buio) - BST: 570",
                "Umbreon - BST: 525",
                "Zoroark - BST: 510",
                "Mabosstiff - BST: 505",
                "Absol - BST: 465",
                "Thievul - BST: 455",
                "Liepard - BST: 446",
                "Persian di Alola - BST: 440",
                "Mightyena - BST: 420",
                "Maschiff - BST: 340",
                "Zorua - BST: 330",
                "Meowth di Alola - BST: 290",
                "Purrloin - BST: 281",
                "Nickit - BST: 245",
                "Poochyena - BST: 220"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Acciaio
        buttonAcciaio.setOnClickListener {
            val info = tipoInfo["Acciaio"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonAcciaio.setTextColor(getColor(android.R.color.system_neutral1_800))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonFolletto.setTextColor(getColor(R.color.white))

            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br> Acciaio⚙️:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Acciaio) - BST: 720",
                "Aggron Mega - BST: 630",
                "Melmetal - BST: 600",
                "Registeel - BST: 580",
                "Silvally (Acciaio) - BST: 570",
                "Klinklang - BST: 520",
                "Perrserker - BST: 440",
                "Klang - BST: 440",
                "Cufant - BST: 330",
                "Klink - BST: 300",
                "Meltan - BST: 300",
                "Meowth di Galar - BST: 290"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

// Bottone Folletto
        buttonFolletto.setOnClickListener {
            val info = tipoInfo["Folletto"]
            val Efficace = info?.superEff?.joinToString(", ")
            val pocoEff = info?.pocoEff?.joinToString(", ")
            val immune = info?.immune?.joinToString(", ")
            buttonFolletto.setTextColor(getColor(android.R.color.holo_purple))
            buttonNormale.setTextColor(getColor(R.color.white))
            buttonFuoco.setTextColor(getColor(R.color.white))
            buttonAcqua.setTextColor(getColor(R.color.white))
            buttonErba.setTextColor(getColor(R.color.white))
            buttonElettro.setTextColor(getColor(R.color.white))
            buttonGhiaccio.setTextColor(getColor(R.color.white))
            buttonLotta.setTextColor(getColor(R.color.white))
            buttonVeleno.setTextColor(getColor(R.color.white))
            buttonTerra.setTextColor(getColor(R.color.white))
            buttonVolante.setTextColor(getColor(R.color.white))
            buttonPsico.setTextColor(getColor(R.color.white))
            buttonColeottero.setTextColor(getColor(R.color.white))
            buttonRoccia.setTextColor(getColor(R.color.white))
            buttonSpettro.setTextColor(getColor(R.color.white))
            buttonDrago.setTextColor(getColor(R.color.white))
            buttonBuio.setTextColor(getColor(R.color.white))
            buttonAcciaio.setTextColor(getColor(R.color.white))



            textMessaggio.text = Html.fromHtml(
                "DANNI INFLITTI <br> Folletto🧚:<br>" +
                        "<font color='#00AA00'>x2</font>: $Efficace<br>" +
                        "<font color='#FF0000'>½</font>: $pocoEff<br>"+
                        "<font color='#808080'>x0</font>:$immune",
                Html.FROM_HTML_MODE_LEGACY
            )
            searchEditText.visibility=View.VISIBLE
            val items = listOf(
                "Arceus (Folletto) - BST: 720",
                "Xerneas - BST: 680",
                "Zacian (Hero of Many Battles) - BST: 670",
                "Silvally (Folletto) - BST: 570",
                "Florges - BST: 552",
                "Sylveon - BST: 525",
                "Alcremie - BST: 495",
                "Comfey - BST: 485",
                "Clefable - BST: 483",
                "Slurpuff - BST: 480",
                "Dachsbun - BST: 477",
                "Aromatisse - BST: 462",
                "Granbull - BST: 450",
                "Floette - BST: 371",
                "Spritzee - BST: 341",
                "Swirlix - BST: 341",
                "Fidough - BST: 339",
                "Flabébé - BST: 303",
                "Milcery - BST: 304",
                "Clefairy - BST: 323",
                "Snubbull - BST: 300",
                "Togepi - BST: 245",
                "Cleffa - BST: 218"
            )

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                items
            )

            listView.adapter = adapter



            searchEditText.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}
