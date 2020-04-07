package com.puercha.algo.common.psapi;

import java.util.Arrays;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD.SIZE_T;
import com.sun.jna.platform.win32.WinDef.DWORD;

public class PROCESS_MEMORY_COUNTERS_EX extends Structure
{
  public DWORD cb;
  public DWORD PageFaultCount;
  public SIZE_T PeakWorkingSetSize;
  public SIZE_T WorkingSetSize;
  public SIZE_T QuotaPeakPagedPoolUsage;
  public SIZE_T QuotaPagedPoolUsage;
  public SIZE_T QuotaPeakNonPagedPoolUsage;
  public SIZE_T QuotaNonPagedPoolUsage;
  public SIZE_T PagefileUsage;
  public SIZE_T PeakPagefileUsage;
  public SIZE_T PrivateUsage;

  public static class ByReference extends PROCESS_MEMORY_COUNTERS_EX implements Structure.ByReference
  {
    public ByReference()
    {
    }

    public ByReference(Pointer memory)
    {
      super(memory);
    }
  }

  public PROCESS_MEMORY_COUNTERS_EX()
  {
  }

  public PROCESS_MEMORY_COUNTERS_EX(Pointer memory)
  {
    super(memory);
    read();
  }

  @Override
  protected java.util.List getFieldOrder()
  {
    return Arrays.asList(new String[]{
      "cb",
      "PageFaultCount",
      "PeakWorkingSetSize",
      "WorkingSetSize",
      "QuotaPeakPagedPoolUsage",
      "QuotaPagedPoolUsage",
      "QuotaPeakNonPagedPoolUsage",
      "QuotaNonPagedPoolUsage",
      "PagefileUsage",
      "PeakPagefileUsage",
      "PrivateUsage"
    });
  }
}