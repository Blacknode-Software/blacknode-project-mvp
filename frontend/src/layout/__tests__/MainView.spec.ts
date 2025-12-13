import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { MainView } from '..';

describe('Layout | Main View', () => {
    it('renders properly', () => {
        const wrapper = mount(MainView);
        expect(wrapper.isVisible()).toBeTruthy();
    });

    it('renders with proper text', () => {
        const wrapper = mount(MainView, {
            slots: {
                default: 'Text',
            },
        });
        const text = wrapper.text();
        expect(text).toContain('Text');
    });
});
