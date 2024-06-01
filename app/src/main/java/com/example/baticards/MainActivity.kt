package com.example.baticards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.toSize
import androidx.core.content.ContextCompat.startActivity
import com.example.baticards.ui.theme.BatiCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatiCardsTheme {
                // a surface container using the 'background' color from the theme
                BatiStartScreen {
                    Log.d("MainActivity", "Create Card button clicked")
                    val intent = Intent(this, GreetingFormActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}


//UI for the start screen
@Composable
fun BatiStartScreen(onCreateCardClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                Log.d("BatiStartScreen", "Create Card button clicked")
                onCreateCardClick()
            },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Get Creative",
                fontSize = 20.sp,
                letterSpacing = 2.sp
            )
        }
    }
}

//Greeting Form page
class GreetingFormActivity : ComponentActivity(), GreetingCardHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatiCardsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingForm(this@GreetingFormActivity)
                }
            }
        }
    }

    //Collecting the data from the greeting form
    override fun startGreetingCardActivity(message: String, signature: String, theme: String) {
        val intent = Intent(this, GreetingCardActivity::class.java).apply {
            putExtra(GreetingCardActivity.MESSAGE_EXTRA, message)
            putExtra(GreetingCardActivity.SIGNATURE_EXTRA, signature)
            putExtra(GreetingCardActivity.THEME_EXTRA, theme)
        }
        startActivity(intent)
    }
}

//UI for the greeting form
@Composable
fun GreetingForm(greetingCardHandler: GreetingCardHandler) {
    var message by remember { mutableStateOf("") }
    var signature by remember { mutableStateOf("") }
    var selectedTheme by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Message",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Text(
            text = "Signature",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = signature,
            onValueChange = { signature = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Select a Theme",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        ThemeDropdownMenu(selectedTheme = selectedTheme, onThemeSelected = { selectedTheme = it })

        Button(
            onClick = {
                greetingCardHandler.startGreetingCardActivity(message, signature, selectedTheme)
            },
            modifier = Modifier
                .padding(32.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Craft Card",
                fontSize = 20.sp,
                letterSpacing = 2.sp
            )
        }
    }
}

//function for the theme dropdown menu
@Composable
fun ThemeDropdownMenu(selectedTheme: String, onThemeSelected: (String) -> Unit) {
    val themes = listOf("Artsy", "Classy", "Colorful", "Cute", "Elegant", "Floral", "Simple")
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedTheme,
            onValueChange = {},
            textStyle = TextStyle(fontSize = 22.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            trailingIcon = {
                Icon(
                    if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            themes.forEach { theme ->
                DropdownMenuItem(text = { Text(text = theme) }, onClick = {
                    onThemeSelected(theme)
                    expanded = false
                })
            }
        }
    }
}

interface GreetingCardHandler {
    fun startGreetingCardActivity(message: String, signature: String, theme: String)
}

//Greeting Card page
class GreetingCardActivity : ComponentActivity() {
    companion object {
        const val MESSAGE_EXTRA = "message_extra"
        const val SIGNATURE_EXTRA = "signature_extra"
        const val THEME_EXTRA = "theme_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val message = intent.getStringExtra(MESSAGE_EXTRA) ?: ""
        val signature = intent.getStringExtra(SIGNATURE_EXTRA) ?: ""
        val selectedTheme = intent.getStringExtra(THEME_EXTRA) ?: ""

        setContent {
            BatiCardsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingCard(message, signature, selectedTheme, onBackPressed = { finish() })
                }
            }
        }
    }
}

//UI for the greeting card
@Composable
fun GreetingCard(
    message: String,
    signature: String,
    selectedTheme: String,
    onBackPressed: () -> Unit
) {
    val imageRes = when (selectedTheme.lowercase()) {
        "artsy" -> R.drawable.artsy
        "classy" -> R.drawable.classy
        "colorful" -> R.drawable.colorful
        "cute" -> R.drawable.cute
        "elegant" -> R.drawable.elegant
        "floral" -> R.drawable.floral
        else -> R.drawable.simple
    }

        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Greeting Card Image",
                contentScale = ContentScale.Crop,
                alpha = 0.7F
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    fontSize = 75.sp,
                    lineHeight = 85.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = signature,
                    fontSize = 36.sp,
                    lineHeight = 44.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Button(
            onClick = onBackPressed
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BirthdayCardPreview() {
//    BatiCardsTheme {
//        GreetingImage(
//            message = stringResource(R.string.greeting_text),
//            from = stringResource(R.string.signature_text),
//            modifier = Modifier)
//    }
//}