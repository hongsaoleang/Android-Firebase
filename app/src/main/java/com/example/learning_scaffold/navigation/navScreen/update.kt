package com.example.learning_scaffold.navigation.navScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learning_scaffold.navigation.model.DataFriends
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UpdateScreen(
    navController: NavController,
    documentId: String,
    initialName: String,
    initialAge: Int,
    initialSalary: Double,
    initialGender: String
) {
    // 1. Initialize states with the passed data
    var name by remember { mutableStateOf(initialName) }
    var age by remember { mutableStateOf(initialAge) }
    var salary by remember { mutableStateOf(initialSalary) }
    var gender by remember { mutableStateOf(initialGender) }

    val myLocal = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ){
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age.toString(),
            onValueChange = { age = it.toIntOrNull() ?: 0 },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = salary.toString(),
            onValueChange = { salary = it.toDoubleOrNull() ?: 0.0 },
            label = { Text("Salary") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- UPDATE BUTTON ---
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (name.isBlank() || gender.isBlank()) {
                    Toast.makeText(myLocal, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    // 2. Create the data object (make sure DataFriends has these fields)
                    val updatedFriend = DataFriends(
                        id = documentId,
                        name = name,
                        age = age,
                        salary = salary,
                        gender = gender
                    )
                    // 3. Update Firestore
                    db.collection("data")
                        .document(documentId)
                        .set(updatedFriend)
                        .addOnSuccessListener {
                            Toast.makeText(myLocal, "Updated Successfully!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack() // Returns to previous screen
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(myLocal, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        ) {
            Text(text = "Save Changes")
        }
    }
}