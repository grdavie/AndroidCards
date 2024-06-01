package com.example.baticards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.toSize
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

class GreetingFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatiCardsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingForm()
                }
            }
        }
    }
}

@Composable
fun GreetingText (message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            fontSize = 100.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = from,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun GreetingForm() {
    var message by remember { mutableStateOf("") }
    var signature by remember { mutableStateOf("") }

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

        ThemeDropdownMenu()

        Button(onClick = {
            // Handle button click here
            // You can use 'message', 'signature', and 'selectedTheme' variables here
        },
            modifier = Modifier
                .padding(32.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Craft Card",
                fontSize = 20.sp,
                letterSpacing = 2.sp)
        }
    }
}

@Composable
fun ThemeDropdownMenu () {
    var expanded by remember { mutableStateOf(false) }
    val themes = listOf("Artsy", "Classy", "Colorful", "Cute", "Elegant", "Floral", "Simple")
    var selectedTheme by remember { mutableStateOf("") }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedTheme,
            onValueChange = { selectedTheme = it},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size.toSize() },
            //label = {Text("Theme")},
            textStyle = TextStyle(fontSize = 22.sp),
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable {expanded = !expanded})
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            themes.forEach { theme ->
                DropdownMenuItem(text = { Text(text = theme) }, onClick = {
                    selectedTheme = theme
                    expanded = false
                })
            }
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