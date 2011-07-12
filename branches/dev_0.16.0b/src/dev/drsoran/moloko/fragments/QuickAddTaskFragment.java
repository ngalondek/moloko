package dev.drsoran.moloko.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.R;


public class QuickAddTaskFragment extends Fragment
{
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.quick_add_task_fragment,
                               container,
                               false );
   }
   
}
