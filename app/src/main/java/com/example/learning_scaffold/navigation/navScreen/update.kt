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
import com.example.learning_scaffold.navigation.model.Student
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UpdateScreen(
    navController: NavController,
    documentId: String,
    initialName: String,
    initialAge: Int,
    initialPhone: String,
    initialGender: String
) {
    var name by remember { mutableStateOf(initialName) }
    var age by remember { mutableStateOf(initialAge.toString()) }
    var phone by remember { mutableStateOf(initialPhone) }
    var gender by remember { mutableStateOf(initialGender) }

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Text(text = "Update Student Info", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
        
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (name.isBlank() || age.isBlank() || phone.isBlank() || gender.isBlank()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    val updatedStudent = Student(
                        id = documentId,
                        name = name,
                        age = age.toIntOrNull() ?: 0,
                        phone = phone,
                        gender = gender
                    )
                    db.collection("students")
                        .document(documentId)
                        .set(updatedStudent)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        ) {
            Text(text = "Save Changes")
        }
    }
}