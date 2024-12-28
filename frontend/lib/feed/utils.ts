export const feedUtils = {
    cleanCDATA: (text: string): string => {
        return text.replace(/<!\[CDATA\[(.*?)\]\]>/g, '$1');
    },

    isValidGuid: (guid: string): boolean => {
        return guid !== '/a/';
    },

    formatDate: (date: string): string => {
        return new Date(date).toISOString();
    }
};