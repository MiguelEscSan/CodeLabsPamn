package com.example.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image

import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.example.affirmations.data.Datasource
import com.example.affirmations.model.PokeCard
import com.example.affirmations.ui.theme.AffirmationsTheme
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AffirmationsApp()
                }
            }
        }
    }
}

@Composable
fun AffirmationsApp() {
    val datasource = remember { Datasource() }
    var pokeCardList by remember { mutableStateOf(listOf<PokeCard>()) }


    LaunchedEffect(Unit) {
        datasource.loadPokeCards() { pokeCards ->
            pokeCardList = pokeCards
        }
    }

    AffirmationList(pokeCardList = pokeCardList)
}

@Composable
fun AffirmationList(pokeCardList: List<PokeCard>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(pokeCardList) { pokeCard ->
            AffirmationCard(
                pokeCard = pokeCard,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun AffirmationCard(pokeCard: PokeCard, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),  // Margen interno en la Card
            horizontalArrangement = Arrangement.Center,  // Centra los elementos horizontalmente
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically  // Centra los elementos verticalmente
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokeCard.imageUrl)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = "PokeCard image",
                modifier = Modifier.size(100.dp)
            )
            Text (
                text = "${pokeCard.id} : ${pokeCard.name}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview
@Composable
fun AffirmationCardPreview() {
    AffirmationCard(PokeCard(25, name = "Pikachu", imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/142.png"))
}
