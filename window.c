#include <windows.h>
#include <winternl.h>

#pragma comment(linker, "/SUBSYSTEM:WINDOWS")
#pragma comment(lib, "ntdll.lib")
#pragma comment(lib, "win32u.lib")

#define RTL_CONSTANT_STRING(x) {.Length = sizeof(x) - sizeof(x[0]), .MaximumLength = sizeof(x), .Buffer = x}

typedef struct _CLSMENUNAME
{
    LPSTR ClientAnsiMenuName;
    LPWSTR ClientUnicodeMenuName;
    PUNICODE_STRING MenuName;
} CLSMENUNAME, *PCLSMENUNAME

typedef unsigned short RTL_ATOM, *PRTL_ATOM;

RTL_ATOM NTAPI NtUserRegisterClassExWOW(WNDCLASSEXW *lpwcx,
                                        PUNICODE_STRING pustrClassName,
                                        PUNICODE_STRING pustrCVersion,
                                        PCLSMENUNAME pClassMenuName,
                                        DWORD fnID,
                                        DWORD Flags,
                                        LPDWORD pWow
                                        );

HWND NTAPI NtUserCreateWindowEx(DWORD dwExStyle,
                                PLARGE_STRING plstrClassName,
                                PLARGE_STRING plstrClsVersion,
                                PLARGE_STRING plstrWindowName,
                                DWORD dwStyle,
                                int x,
                                int y,
                                int nWidth,
                                int nHeight,
                                HWND hWndParent,
                                HMENU hMenu,
                                HINSTANCE hInstance,
                                LPVOID lpParam,
                                DWORD dwFlags,
                                PVOID acbiBuffer
                                );

BOOLEAN NTAPI NtUserDestroyWindow(HWND Wnd);

LRESULT WindowProcedure(HWND window,
                        UINT message,
                        WPARAM wParam,
                        LPARAM lParam
                        )
{

}

int wWinMain(
    HINSTANCE instance,
    HINSTANCE prevInstance,
    PWSTR commandline,
    int showMode
    )
{
    UNICODE_STRING className = RTL_CONSTANT_STRING(L"TestClass");
    LARGE_STRING largeClassName = className;

    WNDCLASSEXW wndClass = {0};
    wndClass.cbSize = sizeof(WNDCLASSEX);
    wndClass.lpfnWndProc = WindowProcedure;
    wndClass.hInstance = instance;
    wndClass.lpszClassName = className.Buffer;

    RTL_ATOM classAtom = NtUserRegisterWindowClassExWOW(&wndClass,
                                                        &className,
                                                        &className,
                                                        NULL,
                                                        0,
                                                        0,
                                                        NULL
                                                        );
    if (!classAtom)
    {
        
    }
}

