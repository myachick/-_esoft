function isValid(s) {
    const stack = [];
    const brackets = { ')': '(', ']': '[', '}': '{' };
    
    for (const char of s) {
        if (['(', '[', '{'].includes(char)) {
            stack.push(char); // Добавляем открывающую скобку в стек
        } else {
            // Если стек пуст или скобка не соответствует последней открывающей
            if (stack.length === 0 || brackets[char] !== stack.pop()) {
                return false;
            }
        }
    }
    
    // Если стек пуст — все скобки закрыты корректно
    return stack.length === 0;
}