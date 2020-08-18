#include "stm32f4xx_it.h"
#include "stm32f4xx_hal.h"

extern UART_HandleTypeDef UartHandle;
extern TIM_HandleTypeDef TimHandle2;

void USART1_IRQHandler(void){
	HAL_UART_IRQHandler(&UartHandle);
}

void TIM2_IRQHandler(void)
{
	HAL_TIM_IRQHandler(&TimHandle2); // TIM 인터럽트 Callback 함수
}
