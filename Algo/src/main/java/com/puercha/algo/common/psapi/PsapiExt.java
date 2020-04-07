package com.puercha.algo.common.psapi;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface PsapiExt extends StdCallLibrary
{
  PsapiExt INSTANCE = (PsapiExt) Native.loadLibrary("psapi", PsapiExt.class, W32APIOptions.UNICODE_OPTIONS);

  boolean GetProcessMemoryInfo(HANDLE hProcess,
                               PROCESS_MEMORY_COUNTERS_EX processMemoryCounters,
                               long processMemoryCountersSizeInBytes);

}