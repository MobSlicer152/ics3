// General Win32 API stuff
#include <windows.h>

// UNICODE_STRING and some other things
#include <winternl.h>

// Make this use the Win32 GUI subsystem so no console is created
#pragma comment(linker, "/SUBSYSTEM:WINDOWS")

// Link to the Native API
#pragma comment(lib, "ntdll.lib")

// What buttons to show on the message box
typedef enum _HARDERROR_RESPONSE_OPTION
{
	OptionAbortRetryIgnore,
	OptionOk,
	OptionOkCancel,
	OptionRetryCancel,
	OptionYesNo,
	OptionYesNoCancel,
	OptionShutdownSystem
} HARDERROR_RESPONSE_OPTION, * PHARDERROR_RESPONSE_OPTION;

// What button was clicked
typedef enum _HARDERROR_RESPONSE
{
	ResponseReturnToCaller,
	ResponseNotHandled,
	ResponseAbort,
	ResponseCancel,
	ResponseIgnore,
	ResponseNo,
	ResponseOk,
	ResponseRetry,
	ResponseYes
} HARDERROR_RESPONSE, * PHARDERROR_RESPONSE;

// Native API function to show a message box or cause a blue screen, among other things
extern NTSTATUS NtRaiseHardError(
    NTSTATUS StatusAndFlags,
    ULONG NumberOfParameters,
    ULONG UnicodeStringParameterMask,
    PULONG_PTR Parameters,
    HARDERROR_RESPONSE_OPTION ResponseOption,
    PHARDERROR_RESPONSE Response
    );

// Creates a UNICODE_STRING from a string literal
#define RTL_CONSTANT_STRING(x) {.Length = sizeof(x) - sizeof(x[0]), .MaximumLength = sizeof(x), .Buffer = x}

// Statuses and flags
#define STATUS_SERVICE_NOTIFICATION 0x40000018 // Makes NtRaiseHardError show a string instead of the NTSTATUS
#define HARDERROR_OVERRIDE_ERRORMODE 0x100000000 // Makes it 

// Main function
INT WinMain(HINSTANCE Instance, HINSTANCE PreviousInstance, PSTR CommandLine, INT ShowMode)
{
    // Arguments are passed as an array
    ULONG_PTR Arguments[] = {
        (ULONG_PTR)&(UNICODE_STRING)RTL_CONSTANT_STRING(L"Hello, world!"), // Text of the message box
        (ULONG_PTR)&(UNICODE_STRING)RTL_CONSTANT_STRING(L"Title"), // Title of the message box
        (ULONG_PTR)MB_ICONQUESTION | MB_OK, // Flags for the message box, same as MessageBox
    };

    // User response
    HARDERROR_RESPONSE Response = 0;

    // Some programs like the debugger will interpret an NTSTATUS, so might as well return it
    return NtRaiseHardError(
        STATUS_SERVICE_NOTIFICATION, // | HARDERROR_OVERRIDE_ERRORMODE,
        sizeof(Arguments) / sizeof(Arguments[0]), // This is the number of elements in Arguments
        0b011, // 1 means a parameter is a UNICODE_STRING
        Arguments, // Give it the arguments
        OptionOk, // Only show the OK button
        &Response // Get the button the user pressed because this parameter is required, but this is ignored because the only point is to show the box
        );
}
