function deepClone<T>(source: T, cache = new WeakMap()): T {
    // Обработка примитивов и null/undefined
    if (source === null || typeof source !== 'object') {
        return source;
    }

    // Проверка циклических ссылок
    if (cache.has(source)) {
        return cache.get(source);
    }

    // Обработка специальных объектов
    const constructor = source.constructor;
    
    // Обработка Date
    if (source instanceof Date) {
        return new Date(source) as T;
    }

    // Обработка RegExp
    if (source instanceof RegExp) {
        return new RegExp(source.source, source.flags) as T;
    }

    // Обработка Map
    if (source instanceof Map) {
        const copy = new Map();
        cache.set(source, copy);
        source.forEach((value, key) => {
            copy.set(deepClone(key, cache), deepClone(value, cache));
        });
        return copy as T;
    }

    // Обработка Set
    if (source instanceof Set) {
        const copy = new Set();
        cache.set(source, copy);
        source.forEach(value => {
            copy.add(deepClone(value, cache));
        });
        return copy as T;
    }

    // Обработка Array
    if (Array.isArray(source)) {
        const copy: any[] = [];
        cache.set(source, copy);
        for (let i = 0; i < source.length; i++) {
            copy[i] = deepClone(source[i], cache);
        }
        return copy as T;
    }

    // Обработка обычных объектов
    if (source instanceof Object) {
        // Сохранение прототипа
        const copy = Object.create(
            Object.getPrototypeOf(source),
            Object.getOwnPropertyDescriptors(source)
        );
        cache.set(source, copy);

        // Копирование символов
        const symbolKeys = Object.getOwnPropertySymbols(source);
        for (const key of symbolKeys) {
            copy[key] = deepClone(source[key as keyof T], cache);
        }

        // Копирование обычных свойств
        for (const key of Object.keys(source)) {
            copy[key] = deepClone((source as any)[key], cache);
        }

        return copy;
    }

    // Для остальных объектов возвращаем оригинал (например, функции)
    return source;
}