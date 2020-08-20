#include "stm32f4xx_it.h"
#include "stm32f4xx_hal.h"

extern UART_HandleTypeDef UartHandle;

void USART1_IRQHandler(void){
	HAL_UART_IRQHandler(&UartHandle);
}

void EXTI0_IRQHandler(void){
	HAL_GPIO_EXTI_IRQHandler(GPIO_PIN_0);
}

void EXTI1_IRQHandler(void){
	HAL_GPIO_EXTI_IRQHandler(GPIO_PIN_1);
}

void EXTI2_IRQHandler(void){
	HAL_GPIO_EXTI_IRQHandler(GPIO_PIN_2);
}
