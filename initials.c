#include <inttypes.h> // integer types
#include <locale.h> // necessary to deal with Unicode (because of Cyrillic)
#include <stdbool.h> // boolean type
#include <stdio.h> // I/O
#include <stdlib.h> // Memory allocation, string conversion
#include <string.h> // String functions
#include <wchar.h> // Wide string functions
#include <wctype.h> // wide character information

// sizeof gives the number of bytes the thing takes up, so this gives the number of elements
#define ARRAY_SIZE(a) (sizeof(a) / sizeof(a[0]))

// Indices of important points
#define FIRST_LATIN 0
#define FIRST_NUMBER 26
#define FIRST_CYRILLIC 36
#define CYRILLIC_YO 68
#define BLANK 69

// 0 means space, 1 means asterisk or other character
static const uint8_t s_font[][6] = {
    { // A
        0b0001000,
        0b0010100,
        0b0100010,
        0b0111110,
        0b0100010,
        0b0100010
    },
    { // B
        0b0111100,
        0b0100010,
        0b0100010,
        0b0111100,
        0b0100010,
        0b0111100
    },
    { // C
        0b0011100,
        0b0100010,
        0b0100000,
        0b0100000,
        0b0100010,
        0b0011100
    },
    { // D
        0b0111100,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0111100
    },
    { // E
        0b0111110,
        0b0100000,
        0b0100000,
        0b0111100,
        0b0100000,
        0b0111110
    },
    { // F
        0b0111110,
        0b0100000,
        0b0100000,
        0b0111100,
        0b0100000,
        0b0100000
    },
    { // G
        0b0011100,
        0b0100010,
        0b0100000,
        0b0100110,
        0b0100010,
        0b0011100
    },
    { // H
        0b0100010,
        0b0100010,
        0b0100010,
        0b0111110,
        0b0100010,
        0b0100010
    },
    { // I
        0b0111110,
        0b0001000,
        0b0001000,
        0b0001000,
        0b0001000,
        0b0111110
    },
    { // J
        0b0111110,
        0b0001000,
        0b0001000,
        0b0001000,
        0b0101000,
        0b0111000
    },
    { // K
        0b0100010,
        0b0100010,
        0b0100100,
        0b0111000,
        0b0100100,
        0b0100010
    },
    { // L
        0b0100000,
        0b0100000,
        0b0100000,
        0b0100000,
        0b0100000,
        0b0111110
    },
    { // M
        0b0100010,
        0b0110110,
        0b0101010,
        0b0100010,
        0b0100010,
        0b0100010
    },
    { // N
        0b0100010,
        0b0110010,
        0b0101010,
        0b0100110,
        0b0100010,
        0b0100010
    },
    { // O
        0b0011100,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0011100
    },
    { // P
        0b0111100,
        0b0100010,
        0b0100010,
        0b0111100,
        0b0100000,
        0b0100000
    },
    { // Q
        0b0011000,
        0b0100100,
        0b0100100,
        0b0100100,
        0b0101100,
        0b0011110
    },
    { // R
        0b0111100,
        0b0100010,
        0b0100010,
        0b0111100,
        0b0100100,
        0b0100010
    },
    { // S
        0b0011100,
        0b0100010,
        0b0110000,
        0b0001100,
        0b0100010,
        0b0011100
    },
    { // T
        0b0111110,
        0b0001000,
        0b0001000,
        0b0001000,
        0b0001000,
        0b0001000
    },
    { // U
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0011100
    },
    { // V
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0010100,
        0b0001000
    },
    { // W
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0101010,
        0b0010100
    },
    { // X
        0b0100010,
        0b0100010,
        0b0010100,
        0b0001000,
        0b0010100,
        0b0100010
    },
    { // Y
        0b0100010,
        0b0100010,
        0b0010100,
        0b0001000,
        0b0001000,
        0b0001000
    },
    { // Z
        0b0111110,
        0b0000100,
        0b0000100,
        0b0001000,
        0b0010000,
        0b0111110
    },
    { // 0
        0b0011100,
        0b0100010,
        0b0100110,
        0b0101010,
        0b0110010,
        0b0011100
    },
    { // 1
        0b0011000,
        0b0101000,
        0b0001000,
        0b0001000,
        0b0001000,
        0b0001000
    },
    { // 2
        0b0011100,
        0b0100010,
        0b0000100,
        0b0001000,
        0b0010000,
        0b0111110
    },
    { // 3
        0b0011100,
        0b0100010,
        0b0000010,
        0b0001100,
        0b0100010,
        0b0011100
    },
    { // 4
        0b0001100,
        0b0010100,
        0b0100100,
        0b0111110,
        0b0000100,
        0b0000100
    },
    { // 5
        0b0111110,
        0b0100000,
        0b0100000,
        0b0111100,
        0b0000010,
        0b0111100
    },
    { // 6
        0b0011110,
        0b0100000,
        0b0100000,
        0b0111100,
        0b0100010,
        0b0011100
    },
    { // 7
        0b0111110,
        0b0000010,
        0b0000100,
        0b0001000,
        0b0001000,
        0b0001000
    },
    { // 8
        0b0011100,
        0b0100010,
        0b0100010,
        0b0011100,
        0b0100010,
        0b0011100
    },
    { // 9
        0b0011100,
        0b0100010,
        0b0011110,
        0b0000010,
        0b0100010,
        0b0011100
    },
    { // A
        0b0001000,
        0b0010100,
        0b0100010,
        0b0111110,
        0b0100010,
        0b0100010
    },
    { // B
        0b0111110,
        0b0100000,
        0b0100000,
        0b0111100,
        0b0100010,
        0b0111100
    },
    { // V
        0b0111100,
        0b0100010,
        0b0100010,
        0b0111100,
        0b0100010,
        0b0111100
    },
    { // G
        0b0111110,
        0b0100000,
        0b0100000,
        0b0100000,
        0b0100000,
        0b0100000
    },
    { // D
        0b0000110,
        0b0001010,
        0b0010010,
        0b0010010,
        0b0111110,
        0b0100010
    },
    { // E
        0b0111110,
        0b0100000,
        0b0100000,
        0b0111100,
        0b0100000,
        0b0111110
    },
    { // Zh
        0b0101010,
        0b0101010,
        0b0101010,
        0b0011100,
        0b0101010,
        0b0101010
    },
    { // Z
        0b0011100,
        0b0100010,
        0b0000010,
        0b0001100,
        0b0100010,
        0b0011100
    },
    { // I
        0b0100010,
        0b0100110,
        0b0101010,
        0b0110010,
        0b0100010,
        0b0100010
    },
    { // J
        0b0010100,
        0b0001000,
        0b0100010,
        0b0100110,
        0b0111010,
        0b0100010
    },
    { // K
        0b0100010,
        0b0100010,
        0b0100100,
        0b0111000,
        0b0100100,
        0b0100010
    },
    { // L
        0b0011110,
        0b0010010,
        0b0010010,
        0b0010010,
        0b0010010,
        0b0100010
    },
    { // M
        0b0100010,
        0b0110110,
        0b0101010,
        0b0100010,
        0b0100010,
        0b0100010
    },
    { // N
        0b0100010,
        0b0100010,
        0b0100010,
        0b0111110,
        0b0100010,
        0b0100010
    },
    { // O
        0b0011100,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0011100
    },
    { // P
        0b0111110,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010
    },
    { // R
        0b0111100,
        0b0100010,
        0b0100010,
        0b0111100,
        0b0100000,
        0b0100000
    },
    { // S
        0b0011100,
        0b0100010,
        0b0100000,
        0b0100000,
        0b0100010,
        0b0011100
    },
    { // T
        0b0111110,
        0b0001000,
        0b0001000,
        0b0001000,
        0b0001000,
        0b0001000
    },
    { // U
        0b0100010,
        0b0100010,
        0b0010100,
        0b0001000,
        0b0010000,
        0b0100000
    },
    { // F
        0b0001000,
        0b0011100,
        0b0101010,
        0b0101010,
        0b0011100,
        0b0001000
    },
    { // Kh
        0b0100010,
        0b0100010,
        0b0010100,
        0b0001000,
        0b0010100,
        0b0100010
    },
    { // Ts
        0b0100100,
        0b0100100,
        0b0100100,
        0b0100100,
        0b0111110,
        0b0000010
    },
    { // Ch
        0b0100010,
        0b0100010,
        0b0011110,
        0b0000010,
        0b0000010,
        0b0000010
    },
    { // Sh
        0b0101010,
        0b0101010,
        0b0101010,
        0b0101010,
        0b0101010,
        0b0111110,
    },
    { // Shch
        0b0101010,
        0b0101010,
        0b0101010,
        0b0101010,
        0b0101010,
        0b0111111
    },
    { // Hard sign
        0b0000000,
        0b0110000,
        0b0010000,
        0b0011100,
        0b0010010,
        0b0011100
    },
    { // Y
        0b0101000,
        0b0101000,
        0b0101000,
        0b0101100,
        0b0101010,
        0b0101100
    },
    { // Soft sign
        0b0000000,
        0b0100000,
        0b0100000,
        0b0111100,
        0b0100010,
        0b0111100
    },
    { // Eh
        0b0111100,
        0b0000010,
        0b0000010,
        0b0001110,
        0b0000010,
        0b0111100
    },
    { // Yu
        0b0100100,
        0b0101010,
        0b0101010,
        0b0111010,
        0b0101010,
        0b0100100
    },
    { // Ya
        0b0011110,
        0b0100010,
        0b0100010,
        0b0011110,
        0b0100010,
        0b0100010
    },
    { // Yo
        0b0010100,
        0b0111110,
        0b0100000,
        0b0111100,
        0b0100000,
        0b0111110
    },
    { // Blank
        0
    }
};

void DrawString(const wchar_t* string, const wchar_t* blank, const wchar_t* filled)
{
    // Get the length of the string beforehand, because calling wcslen
    // repeatedly in the second level of the loop would be slow
    size_t length = wcslen(string);

    const wchar_t* blankStr = blank;
    const wchar_t* filledStr = filled;

    if (!blankStr)
    {
        blankStr = L" ";
    }
    if (!filledStr)
    {
        filledStr = L"*";
    }

    // Go through the rows of the characters
    for (uint8_t i = 0; i < sizeof(s_font[0]); i++)
    {
        // Go through the characters
        for (size_t j = 0; j < length; j++)
        {
            size_t index = BLANK; // Default is blank
            wchar_t c = string[j];

            // Get table index from ASCII character
            if (iswalpha(c))
            {
                wchar_t upper = towupper(c);
                if (upper >= L'А' && upper <= L'Я' || upper == L'Ё') // Cyrillic letters
                {
                    if (upper == L'Ё') // Yo is far enough separate in value
                                       // that it would increase the size of the table
                    {
                        index = CYRILLIC_YO; // There are 32 of the 33 letters before yo in the table,
                                                     // even though that's wrong
                    }
                    else
                    {
                        index = FIRST_CYRILLIC + upper - L'А'; // Cyrillic A not Latin A
                    }
                }
                else if (upper >= L'A' && upper <= L'Z') // Latin letters
                {
                    index = FIRST_LATIN + upper - L'A';
                }
            }
            else if (iswdigit(c))
            {
                // End of letters + first digit
                index = FIRST_NUMBER + c - L'0';
            }

            // Go through the bits
            for (int8_t k = 7; k >= 0; k--)
            {
                // Shift the bits right so the current one is at the right, then discard the
                // ones left of it
                bool bit = (s_font[index][i] >> k) & 0b00000001;
                if (bit)
                {
                    wprintf(L"%s", filledStr);
                }
                else
                {
                    wprintf(L"%s", blankStr);
                }
            }
        }
	
	wprintf(L"\n");
    }
}

int32_t main(int32_t argc, char* argv[])
{
    // Set the locale so Unicode works
    setlocale(LC_ALL, ".UTF-8");

    if (argc > 1)
    {
        // Initialize to NULL so they can be checked for whether they're initialized
        wchar_t* argv1 = NULL;
        wchar_t* argv2 = NULL;
        wchar_t* argv3 = NULL;

        // Allocate memory and check success (+1 is for the NUL terminator not counted by strlen)
        argv1 = calloc(strlen(argv[1] + 1), sizeof(wchar_t));
        if (!argv1)
        {
            exit(1);
        }
        mbstowcs(argv1, argv[1], strlen(argv[1]) + 1);
        if (argc > 2)
        {
            argv2 = calloc(strlen(argv[2] + 1), sizeof(wchar_t));
            if (!argv2)
            {
                exit(1);
            }
            mbstowcs(argv2, argv[2], strlen(argv[2]) + 1);
        }
        if (argc > 3)
        {
            argv3 = calloc(strlen(argv[3] + 1), sizeof(wchar_t));
            if (!argv3)
            {
                exit(1);
            }
            mbstowcs(argv3, argv[3], strlen(argv[3]) + 1);
        }

        // Draw the first argument, if there's a second argument use it as the
        // "filled" parameter, if there's a third, use it as the "blank" parameter
        DrawString(argv1, // draw argv[1]
                   argv3 ? argv3 : L" ", // if argv2, use argv[3]
                   argv2 ? argv2 : L"*" // if argv2, use argv[2]
                   );
    }
    else
    {
        DrawString(L"EEBM",
                   L" ",
                   L"*"
                   );
    }
}

