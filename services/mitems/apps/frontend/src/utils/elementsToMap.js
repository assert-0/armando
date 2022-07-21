export default elements => {
    return elements.reduce((acc, element) => {
        acc[element.id] = element;
        return acc;
    }, {});
};