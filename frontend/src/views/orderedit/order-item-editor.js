import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-combo-box/src/vaadin-combo-box.js';
import '../../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

class OrderItemEditor extends PolymerElement {
  static get template() {
    return html`
<style include="shared-styles">
      .product {
        margin-bottom: 1em;
      }

      .delete {
        min-width: 2em;
        padding: 0;
      }

      @media (max-width: 700px) {
        vaadin-form-layout {
          --vaadin-form-layout-column-spacing: 1em;
        }
      }

      .money {
        text-align: right;
        line-height: 2.5em;
      }

      /* Workaround for vertically distorted elements inside a flex container in IE11 */
      .self-start {
        align-self: flex-start;
      }
    </style>
<vaadin-form-layout id="form1">
 <vaadin-form-layout id="form2" colspan="16" class="product" style="flex: auto;">
  <vaadin-combo-box id="dominios" colspan="6" index="[[index]]" placeholder="Domínio"></vaadin-combo-box>
  <vaadin-combo-box id="classes" colspan="6" index="[[index]]" placeholder="Classe"></vaadin-combo-box>
  <vaadin-combo-box id="diagnosticos" colspan="12" index="[[index]]" placeholder="Diagnóstico"></vaadin-combo-box>
 </vaadin-form-layout>
 <vaadin-button class="delete self-start" id="delete" colspan="2" index="[[index]]">
  <iron-icon icon="vaadin:close"></iron-icon>
 </vaadin-button>
</vaadin-form-layout>
`;
  }

  static get is() {
    return 'order-item-editor';
  }

  ready() {
    super.ready();

    // Not using attributes since Designer does not suppor single-quote attributes
    this.$.form1.responsiveSteps = [
      {columns: 24}
    ];
    this.$.form2.responsiveSteps = [
      {columns: 8, labelsPosition: 'top'},
      {minWidth: '500px', columns: 16, labelsPosition: 'top'}
    ];
  }
}

window.customElements.define(OrderItemEditor.is, OrderItemEditor);
