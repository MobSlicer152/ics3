#include <ctype.h> // character information
#include <inttypes.h> // integer types
#include <stdbool.h> // boolean type
#include <stdio.h> // I/O

// sizeof gives the number of bytes the thing takes up, so this gives the number of elements
#define ARRAY_SIZE(a) (sizeof(a) / sizeof(a[0]))

// 0 means space, 1 means asterisk or other character
static const uint8_t s_font[37][6] = {
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
        0b0011100,
    },
    { // D
        0b0111100,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0100010,
        0b0111100,
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
        0b0100000,
    },
    { // G
        0b0011100,
        0b0100010,
        0b0100000,
        0b0100110,
        0b0100010,
        0b0011100,
    },
    { // H
        0b0100010,
        0b0100010,
        0b0100010,
        0b0111110,
        0b0100010,
        0b0100010,
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
        0b0111000,
    },
    { // K
        0b0100010,
        0b0100010,
        0b0100100,
        0b0111000,
        0b0100100,
        0b0100010,
    },
    { // L
        0b0100000,
        0b0100000,
        0b0100000,
        0b0100000,
        0b0100000,
        0b0111110,
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
        0b0100010,
    },
    { // S
        0b0011100,
        0b0100010,
        0b0110000,
        0b0001100,
        0b0100010,
        0b0011100,
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
        0b0100010,
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
        0b0000100,
        0b0001100,
        0b0100010,
        0b0011100,
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
    { // Blank
        0
    }
};

void DrawString(const char* string, const char* blank, const char* filled)
{
    // Get the length of the string beforehand, because calling strlen
    // repeatedly in the second level of the loop would be slow
    size_t length = strlen(string);
    
    const char* blankStr = blank;
    const char* filledStr = filled;

    if (!blankStr)
    {
        blankStr = " ";
    }
    if (!filledStr)
    {
        filledStr = "*";
    }

    // Go through the rows of the characters
    for (uint8_t i = 0; i < sizeof(s_font[0]); i++)
    {
        // Go through the characters
        for (size_t j = 0; j < length; j++)
        {
            size_t index = ARRAY_SIZE(s_font) - 1; // Last index is blank
            char c = string[j];

            // Get table index from ASCII character
            if (isalpha(c))
            {
                index = toupper(c) - 'A';
            }
            else if (isdigit(c))
            {
                // End of letters - first digit
                index = 'Z' + c - '0';
            }

            // Go through the bits
            for (int8_t k = 7; k >= 0; k--)
            {
                // Shift the bits right so the current one is at the right, then discard the
                // ones left of it
                bool bit = (s_font[index][i] >> k) & 0b00000001;
                if (bit)
                {
                    printf(filledStr);
                }
                else
                {
                    printf(blankStr);
                }
            }
        }

        // Put a newline after each row
        putchar('\n');
        putchar('\r');
    }
}

int32_t main(int32_t argc, char* argv[])
{
    if (argc > 1)
    {
        // Draw the first argument, if there's a second argument use it as the
        // "filled" parameter, if there's a third, use it as the "blank parameter"
        DrawString(argv[1], // draw argv[1]
                   (argc > 3) ? argv[3] : " ", // if argc > 3, use argv[3]
                   (argc > 2) ? argv[2] : "*" // if argc > 2, use argv[2]
                   );
    }
    else
    {
        DrawString("EEBM",
                   " ",
                   "*"
                   );
    }
}

