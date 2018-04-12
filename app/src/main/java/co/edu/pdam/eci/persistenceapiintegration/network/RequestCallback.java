package co.edu.pdam.eci.persistenceapiintegration.network;

/**
 * Created by JuanArevaloMerchan on 11/04/2018.
 */

public interface RequestCallback<T>{

    void onSuccess( T response );

    void onFailed( NetworkException e );
}
