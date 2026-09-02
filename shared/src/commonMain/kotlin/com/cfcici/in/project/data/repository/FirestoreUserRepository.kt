package com.cfcici.`in`.project.data.repository

import com.cfcici.`in`.project.data.database.User
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.QuerySnapshot
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// created a class for firebase-firestore related just like regular UserRepository
class FirestoreUserRepository{
    private val userCollection = Firebase.firestore.collection("users") // get a reference to the "lusers" collection in Firebase
    suspend fun syncUserToFirestore(authUserId: String, user: User){

        // Firestore documents are stored as key-value pairs, not Kotlin object directly
        //So map helps to do that
        val userMap = mapOf(
            "usernameUser" to user.usernameUser,
            "dateOfBirthUser" to user.dateOfBirthUser,
            "emailIdUser" to user.emailIdUser,
            "userPhotoUser" to user.userPhotoUser
        )

        // .document(authUserId) picks or create a special document inside the collection, named using the person's Auth uid users/8M3R0GhjnyZX5N5HVIj4 for example.
        //set(userMap) writes that whole map into the document, overwriting anything already there.
        userCollection.document(authUserId).set(userMap)
    }

    //his sets up an ongoing live connection: it returns a stream that emits a new User (or null)
    // every time that document changes in Firestore, from anywhere — your app, the console, another device.
    fun observeUser(authId: String): Flow<User?>{

        // .snapshot is GitLive's live-listener everytime if there is a change
        return userCollection.document(authId).snapshots.map { snapshot: DocumentSnapshot ->

            //If the document was deleted or never existed, stop and emit null for this update
            // instead of crashing trying to read fields that aren't there.
            if(!snapshot.exists) return@map null

            //Rebuilds a User object from the raw Firestore data
            User(
                usernameUser = snapshot.get("usernameUser") ?: "",
                passwordUser = "",
                dateOfBirthUser = snapshot.get("dateOfBirthUser") ?: "",
                emailIdUser = snapshot.get("emailIdUser") ?: "",
                userPhotoUser = snapshot.get("userPhotoUser")
            )
        }
    }

    fun observeAllUser(): Flow<List<User>> {
        return userCollection.snapshots.map { querySnapshot: QuerySnapshot ->
            querySnapshot.documents.map { doc: DocumentSnapshot ->
                User(
                    usernameUser = doc.get<String>("usernameUser"),
                    passwordUser = "",
                    dateOfBirthUser = doc.get<String>("dateOfBirthUser"),
                    emailIdUser = doc.get<String>("emailIdUser"),
                    userPhotoUser = doc.get<String?>("userPhotoUser")
                )
            }
        }
    }
}


/*
Firebase Auth = its own separate system, already storing email + password (hashed) securely.
                You don't create a "table" for this yourself — Firebase manages it entirely;
                you only see it in the Authentication tab of the console, not Firestore.

Firestore users collection = the separate "table" for everything else — username, dob, photo.
                This is what the repository I wrote does. It never touches password at all.
*/