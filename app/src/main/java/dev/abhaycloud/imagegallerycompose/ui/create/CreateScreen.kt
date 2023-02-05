package dev.abhaycloud.imagegallerycompose.ui.create

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.abhaycloud.imagegallerycompose.R
import dev.abhaycloud.imagegallerycompose.ui.theme.ButtonBg
import dev.abhaycloud.imagegallerycompose.ui.theme.ScreenBg

@Composable
fun CreateScreen(viewModel: CreateImageViewModel = hiltViewModel(), navController: NavController) {

    val nameValue = remember {
        mutableStateOf("")
    }

    val promptValue = remember {
        mutableStateOf("")
    }

    val isImageGenerating = remember {
        mutableStateOf(false)
    }

    val base64Image = remember {
        mutableStateOf("")
    }

    val shareButtonText = remember {
        mutableStateOf("Share with community")
    }


    val result = viewModel.imageResponse.value

    val shareImageResponse = viewModel.shareImage.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ScreenBg),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.open_ai), contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
                        .size(width = 112.dp, height = 30.dp),
                    alignment = Alignment.Center
                )
            }
            Divider()
        }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Create",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Create imaginative and visually stunning images through DALL-E AI and share them with the community!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666E75),
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = "Your name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = nameValue.value,
                onValueChange = {
                    nameValue.value = it
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(45.dp),
                placeholder = {
                    Text(
                        text = "John Doe",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.Gray,
                        ),
                    )
                },
            )

            Row(
                modifier = Modifier.padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Prompt",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )

                Button(
                    onClick = {
                        promptValue.value = surpriseMe()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFECECF1),
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .height(35.dp)
                ) {
                    Text(
                        text = "Surprise me",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                        )
                    )
                }

            }

            OutlinedTextField(
                value = promptValue.value,
                onValueChange = {
                    promptValue.value = it
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(45.dp)
                    .padding(top = 10.dp),
                placeholder = {
                    Text(
                        text = "A 3D rendered planet",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.Gray,
                        ),
                    )
                },
            )

            Button(
                onClick = {
                    if (promptValue.value.isNotEmpty()) {
                        isImageGenerating.value = !isImageGenerating.value
                        viewModel.createImage(promptValue.value)
                    }
                },
                enabled = !isImageGenerating.value && promptValue.value.isNotEmpty(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF15803D),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(top = 15.dp)
            ) {
                Text(
                    text = "Generate",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            if (result.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
                Log.d("myapp", "loading")
            }

            if (result.error.isNotBlank()) {
                Log.d("myapp", result.error)
            }

            result.data?.let {
                isImageGenerating.value = false
                base64Image.value = it.photo
                decodeBase64(it.photo).asImageBitmap()
            }?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier.padding(top = 15.dp),
                    alignment = Alignment.Center
                )
            }


            Button(
                onClick = {
                    if (promptValue.value.isNotEmpty() && nameValue.value.isNotEmpty() && base64Image.value.isNotEmpty()) {
                        viewModel.shareImage(nameValue.value, promptValue.value, "data:image/jpeg;base64,${base64Image.value}")
                    }
                },
                enabled = promptValue.value.isNotEmpty() && nameValue.value.isNotEmpty() && base64Image.value.isNotEmpty(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonBg,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(top = 15.dp)
            ) {
                Text(
                    text = shareButtonText.value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            if (shareImageResponse.isLoading){
                shareButtonText.value = "Sharing..."
            }

            if (shareImageResponse.error.isNotBlank()){
                shareButtonText.value = shareImageResponse.error
            }

            shareImageResponse.data?.let {
                if (shareImageResponse.data.success){
                    navController.popBackStack()
                }
            }

        }
    }
}

private fun decodeBase64(encodedString: String): Bitmap {

    val imageBytes = Base64.decode(encodedString, Base64.DEFAULT)

    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

private fun surpriseMe(): String {
    return listOf(
        "A photo of a white fur monster standing in a purple room",
        "A man wanders through the rainy streets of Tokyo, with bright neon signs, 50mm",
        "A hamburger in the shape of a Rubik’s cube, professional food photography",
        "Synthwave aeroplane",
        "The long-lost Star Wars 1990 Japanese Anime",
        "3D render of a cute tropical fish in an aquarium on a dark blue background, digital art",
        "a fortune-telling shiba inu reading your fate in a giant hamburger, digital art",
        "a painting of a fox in the style of Starry Night",
        "A BBQ that is alive, in the style of a Pixar animated movie",
        "teddy bears shopping for groceries in Japan, ukiyo-e",
        "a bowl of soup that looks like a monster, knitted out of wool"
    ).random()
}