#include <errno.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
    long double n;
    long double total1;
    long double total2;

    if ( argc < 2 )
    {
        printf("Need n\n");
        exit(EINVAL);
    }

    n = strtold(
        argv[1],
        NULL
        );
    if ( n <= 0 )
    {
        printf("Need positive n\n");
        exit(EINVAL);
    }

    total1 = 0;
    //for ( long double i = 1; i < n + 1; i++ )
    //{
    //    total1 += i;
    //}

    total2 = (n + 1) * n / 2.0;

    printf("total1: %.0Lf, total2 = %.0Lf\n", total1, total2);
}
