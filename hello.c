#include <stdint.h>
#include <stdio.h>

int32_t main(void)
{
    printf("Hello, world\n");

    for (uint8_t i = 1; i <= 5; i++)
    {
        printf("i = %hhd\n", i);
    }
}

