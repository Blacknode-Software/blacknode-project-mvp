import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { CustomButton } from '..';

describe('UI Toolkit | Button', () => {
    it('renders properly', () => {
        const wrapper = mount(CustomButton);
        expect(wrapper.isVisible()).toBeTruthy();
    });

    it('renders with default size', () => {
        const wrapper = mount(CustomButton);
        expect(wrapper.classes()).contains(
            'size-medium',
            "button doesn't contain 'size-medium' class by default",
        );
    });

    it('renders with default variant', () => {
        const wrapper = mount(CustomButton);
        expect(wrapper.classes()).contains(
            'variant-filled',
            "button doesn't contain 'variant-filled' class by default",
        );
    });

    it('renders with specified size', () => {
        const wrapper = mount(CustomButton, {
            props: {
                size: 'big',
            },
        });

        expect(wrapper.classes()).contains(
            'size-big',
            "button doesn't contain specified size class",
        );
    });

    it('renders with specified variant', () => {
        const wrapper = mount(CustomButton, {
            props: {
                variant: 'text',
            },
        });

        expect(wrapper.classes()).contains(
            'variant-text',
            "button doesn't contain specified variant class",
        );
    });
});
