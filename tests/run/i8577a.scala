object Macro:
  opaque type StrCtx = StringContext
  def apply(ctx: StringContext): StrCtx = ctx
  def unapply(ctx: StrCtx): Option[StringContext] = Some(ctx)

extension (ctx: StringContext) def mac: Macro.StrCtx = Macro(ctx)
extension (inline ctx: Macro.StrCtx) inline def unapplySeq(inline input: Int): Option[Seq[Int]] =
  Some(Seq(input))

@main def Test: Unit =
  val mac"$x" = 1
  val y: Int = x
  assert(x == 1)
