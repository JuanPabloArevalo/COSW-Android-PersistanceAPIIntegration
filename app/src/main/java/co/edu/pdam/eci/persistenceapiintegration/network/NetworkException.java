package co.edu.pdam.eci.persistenceapiintegration.network;

/**
 * Created by JuanArevaloMerchan on 11/04/2018.
 */

public class NetworkException extends Exception {

    public NetworkException(int i, Object o, Exception e){
        e.printStackTrace();
    }
}
