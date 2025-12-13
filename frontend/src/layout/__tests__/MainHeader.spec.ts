import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { MainHeader } from '..';

describe('Layout | Main Header', () => {
    it('renders properly', () => {
        const wrapper = mount(MainHeader);
        expect(wrapper.isVisible()).toBeTruthy();
    });

    it('renders with proper text', () => {
        const wrapper = mount(MainHeader, {
            props: {
                sections: ['TEXT1', 'TEXT2'],
            },
        });
        const text = wrapper.text();
        expect(text).toContain('TEXT1#TEXT2');
    });
});
