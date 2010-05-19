package dev.drsoran.moloko.service.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import android.os.Handler;
import dev.drsoran.moloko.service.IRtmService;


public interface ILazyConnector
{
   public void connectService( IRtmService service,
                               Handler handler,
                               ExecutorService executor ) throws InterruptedException,
                                                         ExecutionException;
   


   public boolean isConnected();
   


   public void disconnectService();
}
