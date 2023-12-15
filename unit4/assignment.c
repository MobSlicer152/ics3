#include <stdio.h>
#include <stdlib.h>

#define ARRAY_SIZE(A) (sizeof(A) / sizeof(A[0]))

void
SumArrays(
    int* SourceArray1,
    int* SourceArray2,
    int* DestinationArray,
    size_t Size
    )
{
    size_t i;

    for ( i = 0; i < Size; i++ )
    {
        DestinationArray[i] = SourceArray1[i] + SourceArray2[Size - i - 1];
    }
}

int main(void)
{
    int Array1[4] = {9, 2, 1, 4};
    int Array2[4] = {3, 5, 1, 0};
    int OutArray[4] = {0};

    SumArrays(
        Array1,
        Array2,
        OutArray,
        ARRAY_SIZE(OutArray)
        );

    printf("int OutArray[4] = {%d, %d, %d, %d};\n", OutArray[0], OutArray[1], OutArray[2], OutArray[3]);
}

