import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { UniDialog } from '..';

describe('Layout | Uni Dialog', () => {
    it('renders properly', () => {
        const wrapper = mount(UniDialog);
        expect(wrapper.isVisible()).toBeTruthy();
    });

    it('renders with proper text', () => {
        const wrapper = mount(UniDialog, {
            slots: {
                default: 'Text',
            },
        });
        const text = wrapper.text();
        expect(text).toContain('Text');
    });
});
