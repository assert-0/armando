(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5b152437"],{"2ce5":function(t,e,n){"use strict";e["a"]={TEXT:"#2EB100",OPTIONS:"#00aab1",JSON:"#C69A00"}},"70c2":function(t,e,n){"use strict";var i=n("b2f8"),a=n.n(i);a.a},a434:function(t,e,n){"use strict";var i=n("23e7"),a=n("23cb"),s=n("a691"),o=n("50c4"),r=n("7b0b"),l=n("65f0"),c=n("8418"),d=n("1dde"),u=n("ae40"),p=d("splice"),v=u("splice",{ACCESSORS:!0,0:0,1:2}),f=Math.max,m=Math.min,b=9007199254740991,h="Maximum allowed length exceeded";i({target:"Array",proto:!0,forced:!p||!v},{splice:function(t,e){var n,i,d,u,p,v,C=r(this),x=o(C.length),g=a(t,x),w=arguments.length;if(0===w?n=i=0:1===w?(n=0,i=x-g):(n=w-2,i=m(f(s(e),0),x-g)),x+n-i>b)throw TypeError(h);for(d=l(C,i),u=0;u<i;u++)p=g+u,p in C&&c(d,u,C[p]);if(d.length=i,n<i){for(u=g;u<x-i;u++)p=u+i,v=u+n,p in C?C[v]=C[p]:delete C[v];for(u=x;u>x-i+n;u--)delete C[u-1]}else if(n>i)for(u=x-i;u>g;u--)p=u+i-1,v=u+n-1,p in C?C[v]=C[p]:delete C[v];for(u=0;u<n;u++)C[u+g]=arguments[u+2];return C.length=x-i+n,d}})},b2f8:function(t,e,n){},c749:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"editor options-editor"},[i("div",{staticClass:"editor-header"},[i("div",{staticClass:"editor-label",style:{backgroundColor:t.color}},[t._v("Options Editor")])]),i("div",{staticClass:"editor-content"},[i("div",{staticClass:"options-items"},t._l(t.internalContent,(function(e,n){return i("div",{key:n,staticClass:"options-item"},[i("div",{staticClass:"field first"},[i("label",{staticClass:"label"},[t._v("Text")]),i("div",{staticClass:"control"},[i("textarea",{directives:[{name:"model",rawName:"v-model",value:e.text,expression:"item.text"}],staticClass:"input",attrs:{type:"text",placeholder:"Enter a text shown to user"},domProps:{value:e.text},on:{input:function(n){n.target.composing||t.$set(e,"text",n.target.value)}}})])]),i("div",{directives:[{name:"show",rawName:"v-show",value:t.developerMode,expression:"developerMode"}],staticClass:"field first"},[i("label",{staticClass:"label"},[t._v("Internal Value")]),i("div",{staticClass:"control"},[i("input",{directives:[{name:"model",rawName:"v-model",value:e.id,expression:"item.id"}],staticClass:"input",attrs:{type:"text",placeholder:"Enter a value for developers"},domProps:{value:e.id},on:{input:function(n){n.target.composing||t.$set(e,"id",n.target.value)}}})])]),i("div",{staticClass:"field field-button"},[i("button",{staticClass:"button is-danger",on:{click:function(e){return e.preventDefault(),t.removeItem(n)}}},[t._v("Remove")])]),t.internalContent&&t.internalContent.length?t._e():i("p",{staticClass:"notice"},[t._v("Please add options by pressing on the button below.")])])})),0),i("button",{staticClass:"button is-small is-dark add-item-button",on:{click:t.add}},[i("img",{attrs:{src:n("8a37"),alt:""}}),t._v(" Add new option ")])])])},a=[],s=(n("7db0"),n("a434"),n("5530")),o=n("2ce5"),r=n("2f62"),l=function(t){try{var e=JSON.parse(t);return Array.isArray(e)?e:[]}catch(n){return[]}},c={props:{element:{required:!0}},data:function(){return{internalContent:l(this.element.content)}},watch:{internalContent:{deep:!0,handler:function(t){this.updateElement({index:this.element.index,value:JSON.stringify(t)})}},value:function(t){this.internalContent=l(t)}},computed:Object(s["a"])(Object(s["a"])({},Object(r["c"])(["elements","developerMode"])),{},{value:function(){var t=this;return this.elements.find((function(e){return e.index===t.element.index})).content},color:function(){return o["a"]["OPTIONS"]}}),methods:Object(s["a"])(Object(s["a"])({},Object(r["b"])(["updateElement"])),{},{add:function(){this.internalContent.push({text:"",id:""})},removeItem:function(t){this.internalContent.splice(t,1)}})},d=c,u=(n("70c2"),n("2877")),p=Object(u["a"])(d,i,a,!1,null,"626d8abc",null);e["default"]=p.exports}}]);
//# sourceMappingURL=chunk-5b152437.js.map