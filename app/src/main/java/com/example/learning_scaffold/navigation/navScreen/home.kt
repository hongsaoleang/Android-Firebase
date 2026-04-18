package com.example.learning_scaffold.navigation.navScreen

import android.view.RoundedCorner
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learning_scaffold.navigation.model.DataFriends
import com.example.learning_scaffold.ui.theme.Blue200
import com.example.learning_scaffold.ui.theme.Blue500
import com.example.learning_scaffold.ui.theme.Blue600
import com.example.learning_scaffold.ui.theme.Blue700
import com.example.learning_scaffold.ui.theme.Orange
import com.example.learning_scaffold.ui.theme.Red300
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.nio.file.WatchEvent

@Composable
fun ScreenHome(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") } // Store as String for better typing experience
    var salary by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    val saveData = remember { mutableStateListOf<DataFriends>() }
    val myLocal = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // Firestore Listener (Existing logic is fine)
    LaunchedEffect(Unit) {
        db.collection("data").addSnapshotListener { snapshot, error ->
            if (snapshot != null) {
                saveData.clear()
                for (document in snapshot.documents) {
                    val item = document.toObject(DataFriends::class.java)
                    item?.let { saveData.add(it.copy(id = document.id)) }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- INPUT SECTION ---
        Text(text = "Add New Staff", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = name, onValueChange = { name = it },
                label = { Text("Name") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, null) }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = age, onValueChange = { age = it },
                    label = { Text("Age") }, modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = salary, onValueChange = { salary = it },
                    label = { Text("Phone") }, modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            OutlinedTextField(
                value = gender, onValueChange = { gender = it },
                label = { Text("Gender") }, modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                if (name.isBlank() || age.isBlank() || salary.isBlank()) {
                    Toast.makeText(myLocal, "Fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    val newData = DataFriends(name = name, age = age.toInt(), salary = salary.toString(), gender = gender)
                    db.collection("data").add(newData)
                    name = ""; age = ""; salary = ""; gender = ""
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Save")
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(saveData) { data ->
                FriendCard(data,
                    onDelete = { db.collection("data").document(data.id).delete() },
                    onUpdate = { navController.navigate("update/${data.id}/${data.name}/${data.age}/${data.salary}/${data.gender}") }
                )
            }
        }
    }
}

@Composable
fun FriendCard(data: DataFriends, onDelete: () -> Unit, onUpdate: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = data.name, style = MaterialTheme.typography.titleLarge)
                    Text(text = "${data.gender} • ${data.age} years old", color = Color.Gray)
                }
                Text(text = "$${data.salary}", style = MaterialTheme.typography.titleMedium, color = Color(0xFF2E7D32))
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDelete) {
                    Text("Delete", color = Color.Red)
                }
                FilledTonalButton(onClick = onUpdate) {
                    Text("Update")
                }
            }
        }
    }
}