package dev.drsoran.moloko.service.sync;

public final class Constants
{
   public final static String SYNC_EXTRAS_ONLY_SETTINGS = "only_settings";
   
   public static final int SYNC_OBSERVER_TYPE_SETTINGS = 1 << 0;
   
   public static final int SYNC_OBSERVER_TYPE_PENDING = 1 << 1;
   
   public static final int SYNC_OBSERVER_TYPE_ACTIVE = 1 << 2;
   
   public static final int SYNC_OBSERVER_TYPE_STATUS = 1 << 3;
   
   public static final int SYNC_OBSERVER_TYPE_ALL = 0x7fffffff;
}
