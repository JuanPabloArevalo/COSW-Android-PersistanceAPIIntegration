package co.edu.pdam.eci.persistenceapiintegration.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.pdam.eci.persistenceapiintegration.R;
import co.edu.pdam.eci.persistenceapiintegration.data.DBException;
import co.edu.pdam.eci.persistenceapiintegration.data.OrmModel;
import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import co.edu.pdam.eci.persistenceapiintegration.network.NetworkException;
import co.edu.pdam.eci.persistenceapiintegration.network.RequestCallback;
import co.edu.pdam.eci.persistenceapiintegration.network.RetrofitNetwork;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        OrmModel ormModel = new OrmModel();
        ormModel.init(this);
        Team equipo1 = new Team();
        equipo1.setName("Millonarios");
        equipo1.setShortName("Millos");
        equipo1.setImageUrl("https://vignette.wikia.nocookie.net/inciclopedia/images/6/69/Millonarios.jpg/revision/latest?cb=20110705183118");
        try {
            ormModel.getTeamDao().createOrUpdate(equipo1);
        } catch (DBException e) {
            e.printStackTrace();
        }


        try {
            List<Team> teams = ormModel.getTeamDao().getAll();
            for(Team tea: teams){
                Log.i("Nombre",tea.getShortName()+"");
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newFixedThreadPool( 1 );

        executorService.execute( new Runnable(){
            @Override
            public void run(){
                RetrofitNetwork rfN = new RetrofitNetwork();
                rfN.getTeams(new RequestCallback<List<Team>>() {
                    @Override
                    public void onSuccess(List<Team> response) {

                        Log.i("Tamaño",response.size()+"");
                        for(Team tea : response){
                            Log.i("Nombre",tea.getShortName()+"");
                        }
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        e.printStackTrace();
                    }
                });
            }
        } );



    }
}
