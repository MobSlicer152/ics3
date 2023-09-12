#include <ctype.h> // character information
#include <inttypes.h> // integer types
#include <stdbool.h> // boolean type
#include <stdio.h> // I/O

// sizeof gives the number of bytes the thing takes up, so this gives the number of elements
#define ARRAY_SIZE(a) (sizeof(a) / sizeof(a[0]))

// 0 means space, 1 means asterisk or other character
static const uint8_t s_font[37][6] = {
    { // A
        0b00010000,
        0b00101000,
        0b01000100,
        0b01111100,
        0b01000100,
        0b01000100
    },
    { // B
        0b01111100,
        0b01000010,
        0b01000010,
        0b01111100,
        0b01000010,
        0b01111100
    },
    { // C
        0b00111100,
        0b01000010,
        0b01000000,
        0b01000000,
        0b01000010,
        0b00111100,
    },
    { // D
        0b01111100,
        0b01000010,
        0b01000010,
        0b01000010,
        0b01000010,
        0b01111100,
    },
    { // E
        0b01111110,
        0b01000000,
        0b01000000,
        0b01111100,
        0b01000000,
        0b01111110
    },
    { // F
        0b01111110,
        0b01000000,
        0b01000000,
        0b01111100,
        0b01000000,
        0b01000000,
    },
    { // G
        0b00111100,
        0b01000010,
        0b01000000,
        0b01001110,
        0b01000010,
        0b00111100,
    },
    { // H
        0b01000010,
        0b01000010,
        0b01000010,
        0b01111110,
        0b01000010,
        0b01000010,
    },
    { // I
        0b01111110,
        0b00010000,
        0b00010000,
        0b00010000,
        0b00010000,
        0b01111110
    },
    { // J
        0b01111110,
        0b00001000,
        0b00001000,
        0b00001000,
        0b01001000,
        0b01110000,
    },
    { // K
        0b01000010,
        0b01000100,
        0b01001000,
        0b01110000,
        0b01001000,
        0b01000100,
    },
    { // L
        0b01000000,
        0b01000000,
        0b01000000,
        0b01000000,
        0b01000000,
        0b01111110,
    },
    { // M
        0b0100010,
        0b0100010,
        0b0110110,
        0b0101010,
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
        0
    },
    { // P
        0
    },
    { // Q
        0
    },
    { // R
        0
    },
    { // S
        0
    },
    { // T
        0
    },
    { // U
        0
    },
    { // V
        0
    },
    { // W
        0
    },
    { // X
        0
    },
    { // Y
        0
    },
    { // Z
        0
    },
    { // 0
        0
    },
    { // 1
        0
    },
    { // 2
        0
    },
    { // 3
        0
    },
    { // 4
        0
    },
    { // 5
        0
    },
    { // 6
        0
    },
    { // 7
        0
    },
    { // 8
        0
    },
    { // 9
        0
    },
    { // Blank
        0
    }
};

void DrawString(const char* string)
{
    size_t length = strlen(string);

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
                bool bit = (s_font[index][i] >> k) & 0b00000001;
                if (bit)
                {
                    putchar('*');
                }
                else
                {
                    putchar(' ');
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
    if (argc)
    {
        DrawString(argv[1]);
    }
    else
    {
        DrawString("EEBM");
    }
}

