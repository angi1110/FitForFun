package de.schauhuber.philipp.fitforfun;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DatabaseAdapter{

    private FirebaseUser user;
    private Firebase firebaseDatabaseReferenc;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Integer> arrayListLeer;
    private String firebaseStartRef = "https://fitforfun-24.firebaseio.com/User";
    private ArrayList<Integer> DataSet;

//

    public DatabaseAdapter(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabaseReferenc = new Firebase(firebaseStartRef);

    }



    private void setUser() {
        if(firebaseAuth.getCurrentUser()!= null){
        user = firebaseAuth.getCurrentUser();
        }else {

        }

    }

    public void addNewChild(){
        arrayListLeer = new ArrayList<>();
        arrayListLeer.add(0);
        if (getUserEmail()!=null){

        String userEmail = getUserEmail();
        firebaseDatabaseReferenc.child(userEmail).setValue(arrayListLeer);}

    }

    public String getUserEmail(){
        setUser();
        if (user!=null){
            String UserEmail = user.getEmail().replace(".",",");
            return UserEmail;
        } else {
            return null;
        }
    }

    public void SetValueMod(){
        setUser();
        if(user!=null){
            String firebaseReference = firebaseStartRef + "/" + getUserEmail();
            firebaseDatabaseReferenc = new Firebase(firebaseReference);
        }

        firebaseDatabaseReferenc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSet = (ArrayList) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public ArrayList<Integer> getValue(){
        if (DataSet!=null){
            return DataSet;
        }
        return null;
    }

    public void setValue(ArrayList<Integer> newData){
        if(firebaseDatabaseReferenc.toString().equals(firebaseStartRef) ){

        } else {
            firebaseDatabaseReferenc.setValue(newData);
        }

    }

}
