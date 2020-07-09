# Changes in this version

* Delivery client now returns `CompletionStage` and it is async by default
* Place for templates is by default
  * `kentico/templates/`, `META-INF/kentico/templates/`, `kentico/kontent/templates/`, `META-INF/kentico/kontent/templates/` (last two are new)
 * Retry codes are now set statically in DeliveryClient: `408, 429, 500, 502, 503, 504`